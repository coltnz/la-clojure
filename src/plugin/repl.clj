(ns plugin.repl
  (:import (org.jetbrains.plugins.clojure.repl.toolwindow.actions NewConsoleActionBase)
           (org.jetbrains.plugins.clojure.repl REPLProviderBase Response)
           (org.jetbrains.plugins.clojure.repl.impl REPLBase)
           (com.intellij.openapi.actionSystem ActionManager)
           (java.io Writer PrintWriter StringReader)
           (clojure.lang LineNumberingPushbackReader)))

(defn create-repl-out [buffer]
  (PrintWriter.
    (proxy [Writer] []
      (close []
        (.flush this))
      (write [& [x off len]]
        (locking buffer
          (cond
            (number? x) (swap! buffer #(.append #^StringBuilder % (char x)))
            (not off) (swap! buffer #(.append #^StringBuilder % x))
            off (swap! buffer #(.append #^StringBuilder % x off len)))))
      (flush []))))

(defn ide-execute [client-state code]
  (let [code-reader (LineNumberingPushbackReader. (StringReader. code))
        out-buffer (atom (StringBuilder.))
        err-buffer (atom (StringBuilder.))
        values (atom [])
        out (create-repl-out out-buffer)
        err (create-repl-out out-buffer)
        in ""]
    (try
      (clojure.main/repl
        :init #(push-thread-bindings (merge
                                       @client-state
                                       {#'*in*                    (LineNumberingPushbackReader. (StringReader. in))
                                        #'*out*                   out
                                        #'*err*                   err
                                        ; clojure.test captures *out* at load-time, so we need to make sure
                                        ; runtime output of test status/results is redirected properly
                                        #'clojure.test/*test-out* out}))
        :read (fn [prompt exit] (read code-reader false exit))
        :caught (fn [e]
                  (let [repl-exception (clojure.main/repl-exception e)]
                    (swap! client-state assoc #'*e e)
                    (binding [*out* *err*]
                      (prn repl-exception)
                      (flush))))
        :prompt (fn [])
        :need-prompt (constantly false)
        :print (fn [value]
                 (swap! client-state (fn [m]
                                       (with-meta (assoc (get-thread-bindings)
                                                    #'*3 *2
                                                    #'*2 *1
                                                    #'*1 value)
                                                  (meta m))))
                 (swap! values (fn [values] (conj values value)))))
      (finally
        (pop-thread-bindings)
        (.flush out)
        (.flush err)))
    {:value @values,
     :out   (str @out-buffer),
     :err   (str @err-buffer),
     :ns    (str (ns-name (get @client-state #'*ns*)))}))

(defn create-repl [project module console-view working-dir]
  (let [active (atom false)
        client-state (atom {#'*ns* (create-ns 'user)

                            })]
    (proxy [REPLBase] [console-view project]
      (start []
        (swap! active (fn [atom] true)))
      (doStop []
        (swap! active (fn [atom] false)))
      (doExecute [command]
        (let [response (ide-execute client-state command)]
          (proxy [Response] [nil]
            (combinedResponse []
              response)
            (values []
              (:value response)))))
      (isActive [] @active)
      (getType [] "IDE"))))

(defn initialise []
  (let [action (proxy [NewConsoleActionBase] []
                 (getProvider []
                   (proxy [REPLProviderBase] []
                     (isSupported [] true)
                     (newREPL [project module console-view working-dir]
                       (create-repl project module console-view working-dir)))))
        manager (ActionManager/getInstance)
        tools-menu (.getAction manager "ToolsMenu")
        presentation (.getTemplatePresentation action)]
    (.setText presentation "Start IDE Clojure Console")
    (.registerAction manager "NewIDERepl" action)
    (.add tools-menu action)))
