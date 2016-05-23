package eu.qwan

trait Choice

object Choice {
  case object None extends Choice
  case object Cola extends Choice
  case object Fanta extends Choice
  case object Sprite extends Choice
  case object Beer extends Choice
}
