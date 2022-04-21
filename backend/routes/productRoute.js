const {
  addProduct,
  removeProduct,
  updateProduct,
  viewMyProducts,
  productsByTag,
  productsByCategory,
} = require("../controllers/productController");

const Router = require("express").Router();

Router.post("/add/:sid", (req, res) => addProduct(req, res)); //seller id: sid
Router.get("/remove/:sid/:pid", (req, res) => removeProduct(req, res));
Router.post("/updateProduct/:pid", (req, res) => updateProduct(req, res));
Router.get("/view/:uid", (req, res) => viewMyProducts(req, res));
Router.get("/viewByCategory/:cat", (req, res) => productsByCategory(req, res));
Router.get("/viewByTag/:tag", (req, res) => productsByTag(req, res));

module.exports = Router;
