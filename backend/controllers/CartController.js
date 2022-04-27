const User = require("../models/UserModel");

const view = async (req, res) => {
  const uid = req.params.uid;
  const user = await User.findById(uid);
  if (!user)
    return res.status(404).json({ success: false, message: "not found" });
  const cart = user._doc.cart;
  res.json({ success: true, cart });
};

const addToCart = async (req, res) => {
  const uid = req.params.uid,
    pid = req.params.pid;
  const user = await User.findById(uid);
  if (!user)
    return res.status(404).json({ success: false, message: "not found" });
  const cart = user._doc.cart;
  for (item in cart) {
    if (item == pid)
      return res.json({ success: false, message: "product already in cart" });
  }
  User.findByIdAndUpdate(uid, { cart: [...cart, pid] }, function (e, doc) {
    if (e)
      return res
        .status(404)
        .json({ success: false, message: "some error occured" });
    res.json({ success: true, message: "updated successfully" });
  });
};

const removeFromCart = async (req, res) => {
  const uid = req.params.uid,
    pid = req.params.pid;
  const user = await User.findById(uid);
  if (!user)
    return res.status(404).json({ success: false, message: "not found" });
  let cart = user._doc.cart;
  const idx = cart.indexOf(pid);
  cart.splice(idx, 1);
  User.findByIdAndUpdate(uid, { cart: cart }, function (e, doc) {
    if (e)
      return res
        .status(404)
        .json({ success: false, message: "some error occured" });
    res.json({ success: true, message: "updated successfully" });
  });
};

module.exports = { view, addToCart, removeFromCart };
