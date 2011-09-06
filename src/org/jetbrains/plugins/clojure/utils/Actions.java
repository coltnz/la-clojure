package org.jetbrains.plugins.clojure.utils;

import com.intellij.facet.FacetManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.plugins.clojure.config.ClojureFacet;
import org.jetbrains.plugins.clojure.config.ClojureFacetType;

/**
 * @author Colin Fleming
 */
public class Actions
{
  public static Module getModule(AnActionEvent e)
  {
    Module module = e.getData(DataKeys.MODULE);
    if (module == null)
    {
      Project project = e.getData(DataKeys.PROJECT);

      if (project == null)
      {
        return null;
      }
      Module[] modules = ModuleManager.getInstance(project).getModules();
      if (modules.length == 1)
      {
        module = modules[0];
      }
      else
      {
        for (Module m : modules)
        {
          FacetManager manager = FacetManager.getInstance(m);
          ClojureFacet clFacet = manager.getFacetByType(ClojureFacetType.INSTANCE.getId());
          if (clFacet != null)
          {
            module = m;
            break;
          }
        }
        if (module == null)
        {
          module = modules[0];
        }
      }
    }
    return module;
  }
}
