package org.jetbrains.plugins.clojure.repl.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import org.jetbrains.plugins.clojure.ClojureBundle;
import org.jetbrains.plugins.clojure.ClojureIcons;
import org.jetbrains.plugins.clojure.psi.util.ClojurePsiElementFactoryImpl;
import org.jetbrains.plugins.clojure.psi.util.ClojurePsiUtil;

/**
 * @author ilyas
 */
public final class RunTopSExprAction extends RunActionBase
{
  public RunTopSExprAction()
  {
    getTemplatePresentation().setIcon(ClojureIcons.REPL_EVAL);
  }

  public void actionPerformed(AnActionEvent event)
  {
    Editor editor = event.getData(DataKeys.EDITOR);
    if (editor == null)
    {
      return;
    }

    Project project = editor.getProject();
    if (project == null)
    {
      return;
    }

    PsiElement sexp = ClojurePsiUtil.findTopSexpAroundCaret(editor);
    if (sexp == null)
    {
      return;
    }

    if (ClojurePsiElementFactoryImpl.getInstance(project).hasSyntacticalErrors(sexp))
    {
      Messages.showErrorDialog(project,
                               ClojureBundle.message("evaluate.incorrect.sexp"),
                               ClojureBundle.message("evaluate.incorrect.cannot.evaluate"));

      return;
    }

    executeTextRange(editor, sexp.getTextRange());
  }
}
