/**
 * Copyright (c) 2015, Dmitry Ivanov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the IFMO nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.ifunsoftware.c3web

import akka.actor.{ ExtendedActorSystem, ExtensionIdProvider, ExtensionId, Extension }
import com.typesafe.config.Config

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {

  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem): Settings =
    new Settings(system.settings.config.getConfig("c3"))
}

class Settings(config: Config) extends Extension {
  val Http = new HttpSettings(config.getConfig("http"))
  val Timeouts = new TimeoutSettings(config.getConfig("timeouts"))

  class HttpSettings(config: Config) {
    val Interface = config.getString("interface")
    val Port = config.getInt("port")
  }

  class TimeoutSettings(config: Config) {
    import scala.concurrent.duration._

    val DefaultFutureTimeout = FiniteDuration(config.getDuration("default-future-timeout", MILLISECONDS), MILLISECONDS)
  }
}
