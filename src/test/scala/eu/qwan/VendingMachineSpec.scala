package eu.qwan

class VendingMachineSpec extends org.specs2.mutable.Specification {
  "choiceless_machine" should {
    "deliver_nothing" in {
      val machine = new VendingMachine
      machine.deliver(Choice.Cola) === Can.None
      machine.deliver(Choice.Fanta) === Can.None
    }
  }

  "delivers_can_of_choice" in {
    val machine = new VendingMachine
    machine.configure(Choice.Cola, Can.Cola, 10)
    machine.configure(Choice.Fanta, Can.Fanta, 10)
    machine.configure(Choice.Sprite, Can.Sprite, 10)
    machine.deliver(Choice.Cola) === Can.Cola
    machine.deliver(Choice.Fanta) === Can.Fanta
    machine.deliver(Choice.Sprite) === Can.Sprite
  }

  "delivers_nothing_when_making_invalid_choice" in {
    val machine = new VendingMachine
    machine.configure(Choice.Cola, Can.Cola, 10)
    machine.configure(Choice.Fanta, Can.Fanta, 10)
    machine.configure(Choice.Sprite, Can.Sprite, 10)
    machine.deliver(Choice.Beer) === Can.None
  }

  "delivers_nothing_when_not_paid" in {
    val machine = new VendingMachine
    machine.configure(Choice.Fanta, Can.Fanta, 10, 2)
    machine.configure(Choice.Sprite, Can.Sprite, 10, 1)

    machine.deliver(Choice.Fanta) === Can.None
  }

  "delivers_fanta_when_paid" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 10, 1)
    machine.configure(Choice.Fanta, Can.Fanta, 10, 2)

    machine.set_value(2)
    machine.deliver(Choice.Fanta) === Can.Fanta
    machine.deliver(Choice.Sprite) === Can.None
  }

  "delivers_sprite_when_paid" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 10, 1)
    machine.configure(Choice.Fanta, Can.Fanta, 10, 2)

    machine.set_value(2)
    machine.deliver(Choice.Sprite) === Can.Sprite
    machine.deliver(Choice.Sprite) === Can.Sprite
    machine.deliver(Choice.Sprite) === Can.None
  }

  "add_payments" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 10, 1)
    machine.configure(Choice.Fanta, Can.Fanta, 10, 2)

    machine.set_value(1)
    machine.set_value(1)
    machine.deliver(Choice.Sprite) === Can.Sprite
    machine.deliver(Choice.Sprite) === Can.Sprite
    machine.deliver(Choice.Sprite) === Can.None
  }

  "returns_change" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 10, 1)
    machine.set_value(2)
    machine.get_change === 2
    machine.get_change === 0
  }

  "stock" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 1, 0)
    machine.deliver(Choice.Sprite) === Can.Sprite
    machine.deliver(Choice.Sprite) === Can.None
  }

  "add_stock" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 1, 0)
    machine.configure(Choice.Sprite, Can.Sprite, 1, 0)
    machine.deliver(Choice.Sprite) === Can.Sprite
    machine.deliver(Choice.Sprite) === Can.Sprite
    machine.deliver(Choice.Sprite) === Can.None
  }

  "checkout_chip_if_chipknip_inserted" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 1, 1)
    val chip = new Chipknip(10)
    machine.insert_chip(chip)
    machine.deliver(Choice.Sprite) === Can.Sprite
    chip.credits === 9
  }

  "checkout_chip_empty" in {
    val machine = new VendingMachine
    machine.configure(Choice.Sprite, Can.Sprite, 1, 1)
    val chip = new Chipknip(0)
    machine.insert_chip(chip)
    machine.deliver(Choice.Sprite) === Can.None
    chip.credits === 0
  }
}
