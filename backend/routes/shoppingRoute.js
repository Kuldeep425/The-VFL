const {
  addToCart,
  removeFromCart,
  view,
} = require("../controllers/CartController");

const Router = require("express").Router();

Router.get("/cart/view/:uid", (req, res) => view(req, res));
Router.get("/cart/add/:uid/:pid", (req, res) => addToCart(req, res));
Router.get("/cart/remove/:uid/:pid", (req, res) => removeFromCart(req, res));

module.exports = Router;
