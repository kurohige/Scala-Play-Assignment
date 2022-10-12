package controllers

import Models.MemberFormData

import javax.inject._
import Models.{Member, MembersForm}
import play.Logger
import play.api.mvc._
import services.MemberService


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, memberService: MemberService) extends AbstractController(cc){

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action.async { implicit request: Request[AnyContent] =>
    memberService.findAll map { members =>
      Ok(views.html.getmembersclub(MembersForm.form, members))
    }
  }

//  def findMember = Action.async{ implicit  request: Request[AnyContent] =>
//    memberService.findMember map { members =>
//      Redirect(routes.HomeController.index())
//    }

  //}

  def createMember() = Action.async{ implicit request: Request[AnyContent] =>
    MembersForm.form.bindFromRequest().fold(
    //if any error in submitted info
    errorForm =>{
      Logger.warn(s"Form submission with error: ${errorForm.errors}")
      Future.successful(Ok(views.html.index()))
    },
    data => {
      val newMember = Member(0, data.first_name, data.last_name, data.club_name, data.club_initials)
      memberService.addMember(newMember).map( _ => Redirect(routes.HomeController.index()))
    })

  }
  
}
