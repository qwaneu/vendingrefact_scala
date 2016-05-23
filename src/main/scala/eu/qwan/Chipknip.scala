package eu.qwan

/**
  * Created by marc on 23-5-16.
  */
class Chipknip(var credits: Int) {
  def HasValue(p: Int): Boolean = {
    return credits >= p
  }

  def Reduce(p: Int) {
    credits -= p
  }
}
