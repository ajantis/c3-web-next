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
package users

import java.util.UUID

import akka.actor.{ Props, ActorRef, ActorLogging, Actor }
import com.ifunsoftware.c3web.users.UsersStore.{ FetchUserByEmail, FetchUserById }
import domain._

class UsersStore extends Actor with ActorLogging {
  import User._

  // In-memory users store
  val users: Map[Id, User] = Seq(
    User(UUID.randomUUID(), "user1@test.com"),
    User(UUID.randomUUID(), "user2@test.com"),
    User(UUID.randomUUID(), "user3@test.com")).map(u => u.id -> u).toMap

  def receive = {
    case FetchUserById(id)       => fetchUser(id, sender())

    case FetchUserByEmail(email) => fetchUser(email, sender())
  }

  private def fetchUser(id: User.Id, requester: ActorRef): Unit = {
    requester ! users.get(id)
  }

  private def fetchUser(email: User.Email, requester: ActorRef): Unit = {
    requester ! users.find(idAndUser => idAndUser._2.email == email)
  }
}

object UsersStore {
  case class FetchUserById(id: User.Id)
  case class FetchUserByEmail(email: User.Email)

  def props(): Props = Props(new UsersStore)
}
