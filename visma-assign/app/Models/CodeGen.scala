package Models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.H2Profile",
    "org.h2.Driver",
    "jdbc:h2:mem:play",
    "jdbc:h2:tcp://localhost/~/test",
    "models", None, None, true, false
  )

}
