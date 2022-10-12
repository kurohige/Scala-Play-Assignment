package services

import com.google.inject.Inject
import Models.{Member, Members}

import  scala.concurrent.Future

class MemberService @Inject() (members: Members){

  def addMember(member: Member): Future[String] = {
    members.createMember(member)
  }

  def findMember(name: String): Future[Option[Member]] ={
    members.findMember(name)
  }

  def findAll: Future[Seq[Member]] = {
    members.findAll
  }

}
