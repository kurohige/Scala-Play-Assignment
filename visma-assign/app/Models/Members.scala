package Models
import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import  scala.concurrent.{ExecutionContext,Future}
import slick.jdbc.H2Profile.api._


case class Member(id: Int, first_name: String, last_name: String, club_name: String, club_initials: Option[String])

case class MemberFormData(first_name: String, last_name: String, club_name: String, club_initials: Option[String])

object MembersForm {
  val form = Form(
    mapping(
      "first_name" -> nonEmptyText,
      "last_name" -> nonEmptyText,
      "club_name" -> nonEmptyText,
      "club_initials" -> optional(text)
    )(MemberFormData.apply)(MemberFormData.unapply)
  )
}

class MemberTableDef(tag: Tag) extends Table[Member](tag, "MEMBERSCLUB")
{
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def first_name = column[String]("FIRST_NAME")
  def last_name = column[String]("LAST_NAME")
  def club_name = column[String]("CLUB_NAME")
  def club_initials = column[Option[String]]("CLUB_INITIALS")

  override def * =
    (id, first_name,last_name, club_name, club_initials) <> (Member.tupled, Member.unapply _)

}

class Members @Inject() (protected  val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)  extends  HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  val members = TableQuery[MemberTableDef]
  def createMember(member: Member): Future[String] = {
    dbConfig.db.run(members += member).map(res =>"Member succesfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def findMember(name: String) : Future[Option[Member]] = {
//    val query = sql"SELECT first_name, last_name, club_name FROM membersclub where club_name = ${name};".as[String]
//    val queryResult : Future[Option[String]] = db.run(query)
//    queryResult.map(_.headOption)
    dbConfig.db.run(members.filter(_.club_name === name).result.headOption)
  }

  def findAll : Future[Seq[Member]] = {
//    val query = sql"SELECT first_name, last_name, club_name FROM membersclub".as[String]
//    val queryResult: Future[Vector[String]] = db.run(query)
//    queryResult.map(_.headOption)
    dbConfig.db.run(members.result)
  }
}
