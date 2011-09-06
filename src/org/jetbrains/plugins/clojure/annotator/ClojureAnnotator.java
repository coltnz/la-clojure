package org.jetbrains.plugins.clojure.annotator;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.HashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.clojure.highlighter.ClojureSyntaxHighlighter;
import org.jetbrains.plugins.clojure.psi.api.ClList;
import org.jetbrains.plugins.clojure.psi.api.symbols.ClSymbol;

import java.util.Arrays;
import java.util.Set;

/**
 * @author ilyas
 */
public class ClojureAnnotator implements Annotator {
  public static final Set<String> IMPLICIT_NAMES = new HashSet<String>();

  static {
    IMPLICIT_NAMES.addAll(Arrays.asList("def", "new", "throw", "ns", "in-ns", "if", "do"));
  }

  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (element instanceof ClList) {
      annotateList((ClList) element, holder);
    }
    if (element instanceof ClSymbol) {
      ClSymbol symbol = (ClSymbol) element;
      if (symbol.isQualified()) {
        checkNonQualifiedSymbol(symbol, holder);
      }
    }
  }

  private void checkNonQualifiedSymbol(ClSymbol symbol, AnnotationHolder holder) {
    // todo add import fixo
  }

  private void annotateList(ClList list, AnnotationHolder holder) {
    final ClSymbol first = list.getFirstSymbol();
    if (first != null && (first.multiResolve(false).length > 0 ||
            IMPLICIT_NAMES.contains(list.getHeadText()))) {
      Annotation annotation = holder.createInfoAnnotation(first, null);
      annotation.setTextAttributes(ClojureSyntaxHighlighter.DEF);
    }
  }
}
