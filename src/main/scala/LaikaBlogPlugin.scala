import java.io.File

import sbt._
import Keys._
import com.typesafe.sbt.site.SitePlugin
import com.typesafe.sbt.site.util.SiteHelpers
import com.typesafe.sbt.site.SitePlugin.autoImport.siteSubdirName
import laika.sbt.LaikaSbtPlugin
import laika.sbt.LaikaSbtPlugin.{ LaikaKeys, LaikaPlugin }

object LaikaBlogPlugin extends AutoPlugin {
  override def requires = SitePlugin
  override def trigger = noTrigger

  object autoImport {
    val LaikaBlog = config("blog")
  }
  import autoImport._

  override def projectSettings = laikaBlogSettings(LaikaBlog)

  /** Creates settings necessary for running Laika in the given configuration. */
  def laikaBlogSettings(config: Configuration): Seq[Setting[_]] =
    LaikaPlugin.defaults ++
      inConfig(config)(
        Seq(
          includeFilter := AllPassFilter,
          excludeFilter := HiddenFileFilter,
          mappings := generate(
            (mappings in (LaikaKeys.Laika, LaikaKeys.site)).value,
            target.value,
            includeFilter.value,
            excludeFilter.value,
            streams.value
          ),
          siteSubdirName := "",

          // Change defaults of laika to reflect sbt-site setup
          sourceDirectories in LaikaKeys.Laika := Seq(sourceDirectory.value),
          target in LaikaKeys.Laika := target.value / siteSubdirName.value,
          target in (LaikaKeys.Laika, LaikaKeys.site) := (target in Compile).value / "site" / siteSubdirName.value
        )
      ) ++
        SiteHelpers.directorySettings(config) ++
        SiteHelpers.watchSettings(config) ++
        SiteHelpers.addMappingsToSiteDir(mappings in config, siteSubdirName in config)

  private def generate(mappings: scala.Seq[(sbt.File, String)], target: File, inc: FileFilter, exc: FileFilter, s: TaskStreams): Seq[(File, String)] = {
    // Figure out what was generated.
    val files = (target ** inc) --- (target ** exc) --- target
    files pair relativeTo(target)
  }
}
