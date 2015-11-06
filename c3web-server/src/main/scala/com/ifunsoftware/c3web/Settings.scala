package com.ifunsoftware.c3web

import akka.actor.{ ExtendedActorSystem, ExtensionIdProvider, ExtensionId, Extension }
import com.typesafe.config.Config

object Settings extends ExtensionId[SettingsImpl] with ExtensionIdProvider {

  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem): SettingsImpl =
    new SettingsImpl(system.settings.config.getConfig("c3"))
}

class SettingsImpl(config: Config) extends Extension {
  val Http = new HttpSettings(config.getConfig("http"))

  class HttpSettings(config: Config) {
    val Interface = config.getString("interface")
    val Port = config.getInt("port")
  }
}
