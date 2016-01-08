package com.ifunsoftware.c3web.service

import com.ifunsoftware.c3web.data.UserData
import com.ifunsoftware.c3web.models.AuthInfo

import scala.util.Random

/**
 * Created by alexander on 01.11.15.
 */

object AuthenticationService {

  import UserData.userMock

  def authenticateUser(userAuthInfo: AuthInfo): Option[AuthInfo] = {
    userMock.find(_.username == userAuthInfo.username).find(_.password == userAuthInfo.password) match {
      case None       => Option(null)
      case Some(user) => Some(userAuthInfo.copy(api_token = Some("token" + Random.nextGaussian())))
    }
  }
}
