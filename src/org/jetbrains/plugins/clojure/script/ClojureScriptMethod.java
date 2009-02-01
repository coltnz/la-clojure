package org.jetbrains.plugins.clojure.script;

import com.intellij.psi.impl.light.LightElement;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.MethodSignature;
import com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.plugins.clojure.file.ClojureFileType;

import java.util.List;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
 * User: peter
 * Date: Jan 9, 2009
 * Time: 4:22:47 PM
 * Copyright 2007, 2008, 2009 Red Shark Technology
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ClojureScriptMethod extends LightElement implements PsiMethod {
  public static final Logger LOG = Logger.getInstance("org.jetbrains.plugins.Clojure.lang.psi.impl.synthetic.ClojureScriptMethod");
  public PsiMethod myCodeBehindMethod;
  private ClojureScriptClass myScriptClass;

  public ClojureScriptMethod(ClojureScriptClass scriptClass, String codeBehindText) {
    super(scriptClass.getManager(), ClojureFileType.CLOJURE_LANGUAGE);
    myScriptClass = scriptClass;
    PsiElementFactory factory = JavaPsiFacade.getInstance(getProject()).getElementFactory();
    try {
      myCodeBehindMethod = factory.createMethodFromText(codeBehindText, null);
    } catch (IncorrectOperationException e) {
      LOG.error(e);
    }
  }

  @Nullable
  public PsiType getReturnType() {
    return myCodeBehindMethod.getReturnType();
  }

  @Nullable
  public PsiTypeElement getReturnTypeElement() {
    return null;
  }

  @NotNull
  public PsiParameterList getParameterList() {
    return myCodeBehindMethod.getParameterList();
  }

  @NotNull
  public PsiReferenceList getThrowsList() {
    return myCodeBehindMethod.getThrowsList();
  }

  @Nullable
  public PsiCodeBlock getBody() {
    return null;
  }

  public boolean isConstructor() {
    return false;
  }

  public boolean isVarArgs() {
    return false;
  }

  @NotNull
  public MethodSignature getSignature(@NotNull PsiSubstitutor substitutor) {
    return myCodeBehindMethod.getSignature(substitutor);
  }

  @Nullable
  public PsiIdentifier getNameIdentifier() {
    return null;
  }

  @NotNull
  public PsiMethod[] findSuperMethods() {
    return new PsiMethod[0];
  }

  @NotNull
  public PsiMethod[] findSuperMethods(boolean checkAccess) {
    return new PsiMethod[0];
  }

  @NotNull
  public PsiMethod[] findSuperMethods(PsiClass parentClass) {
    return new PsiMethod[0];
  }

  @NotNull
  public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean checkAccess) {
    return Collections.emptyList();
  }

  @Nullable
  public PsiMethod findDeepestSuperMethod() {
    return null;
  }

  @NotNull
  public PsiMethod[] findDeepestSuperMethods() {
    return new PsiMethod[0];
  }

  @NotNull
  public PsiModifierList getModifierList() {
    return myCodeBehindMethod.getModifierList();
  }

  public boolean hasModifierProperty(@NonNls @NotNull String name) {
    return myCodeBehindMethod.hasModifierProperty(name);
  }

  @NotNull
  public String getName() {
    return myCodeBehindMethod.getName();
  }

  public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
    throw new IncorrectOperationException("Cannot set name");
  }

  @NotNull
  public HierarchicalMethodSignature getHierarchicalMethodSignature() {
    return myCodeBehindMethod.getHierarchicalMethodSignature();
  }

  public PsiClass getContainingClass() {
    return myScriptClass;
  }

  @NonNls
  public String getText() {
    return null;
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
  }

  public PsiElement copy() {
    return null;
  }

  public PsiFile getContainingFile() {
    return myScriptClass.getContainingFile();
  }

  public TextRange getTextRange() {
    return myScriptClass.getTextRange();
  }

  public String toString() {
    return "ClojureScriptMethod";
  }

  @Nullable
  public PsiDocComment getDocComment() {
    return null;
  }

  public boolean isDeprecated() {
    return false;
  }

  public boolean hasTypeParameters() {
    return false;
  }

  @Nullable
  public PsiTypeParameterList getTypeParameterList() {
    return null;
  }

  @NotNull
  public PsiTypeParameter[] getTypeParameters() {
    return PsiTypeParameter.EMPTY_ARRAY;
  }
}
