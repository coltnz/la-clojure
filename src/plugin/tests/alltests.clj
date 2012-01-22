(ns plugin.tests.alltests
  (:use [clojure.test :only [run-tests]])
  (:require plugin.tests.typingtests
   plugin.tests.editoractiontests))

(run-tests 'plugin.tests.typingtests
           'plugin.tests.editoractiontests)

; TODO investigate why we need this
(System/exit 0)
