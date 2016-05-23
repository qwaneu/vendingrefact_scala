package eu.qwan

import scala.collection.mutable

class VendingMachine {
  final private val cans = mutable.Map.empty[Choice, CanContainer]
  private var payment_method: Int = 0
  private var chipknip: Chipknip = null
  private var c: Int = -1
  private var price: Int = 0

  def set_value(v: Int) {
    payment_method = 1
    if (c != -1) {
      c += v
    }
    else {
      c = v
    }
  }

  def insert_chip(chipknip: Chipknip) {
    payment_method = 2
    this.chipknip = chipknip
  }

  def deliver(choice: Choice): Can = {
    var res: Can = Can.None
    if (cans.contains(choice)) {
      if (cans(choice).price == 0) {
        res = cans(choice).getType
      }
      else {
        payment_method match {
          case 1 =>
            if (c != -1 && cans(choice).price <= c) {
              res = cans(choice).getType
              c -= cans(choice).price
            }
          case 2 =>
            if (chipknip.HasValue(cans(choice).price)) {
              chipknip.Reduce(cans(choice).price)
              res = cans(choice).getType
            }
          case _ =>
        }
      }
    }
    else {
      res = Can.None
    }
    if (res != Can.None) {
      if (cans(choice).getAmount <= 0) {
        res = Can.None
      }
      else {
        cans(choice).setAmount(cans(choice).getAmount - 1)
      }
    }
    if (res eq Can.None) {
      return Can.None
    }

    res
  }

  def get_change: Int = {
    var to_return: Int = 0
    if (c > 0) {
      to_return = c
      c = 0
    }
    return to_return
  }

  def configure(choice: Choice, c: Can, n: Int) {
    configure(choice, c, n, 0)
  }

  def configure(choice: Choice, c: Can, n: Int, price: Int) {
    this.price = price
    if (cans.contains(choice)) {
      cans(choice).setAmount(cans(choice).getAmount + n)
      return
    }
    val can: CanContainer = new CanContainer
    can.setType(c)
    can.setAmount(n)
    can.setPrice(price)
    cans.put(choice, can)
  }
}
