package eu.qwan

class CanContainer {
  private var `type`: Can = null

  def getType: Can = {
    return `type`
  }

  def setType(`type`: Can) {
    this.`type` = `type`
  }

  var price: Int = 0

  def getPrice: Int = {
    return price
  }

  def setPrice(price: Int) {
    this.price = price
  }

  private var amount: Int = 0

  def getAmount: Int = {
    return amount
  }

  def setAmount(amount: Int) {
    this.amount = amount
  }
}
