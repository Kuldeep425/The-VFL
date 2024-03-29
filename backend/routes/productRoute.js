const {
  addProduct,
  removeProduct,
  updateProduct,
  viewMyProducts,
  viewall,
  viewOne,
  GetAllSortedByCategory
} = require("../controllers/productController");

const Router = require("express").Router();

Router.post("/add/:sid", (req, res) => addProduct(req, res)); //seller id: sid
Router.get("/remove/:sid/:pid", (req, res) => removeProduct(req, res));
Router.post("/update/:sid/:pid", (req, res) => updateProduct(req, res));
Router.get("/view/:sid", (req, res) => viewMyProducts(req, res));
Router.get("/viewall", (req, res) => viewall(req, res));
Router.get("/viewone/:pid", (req, res) => viewOne(req, res));
Router.get("/get-all-category-sorted",(req,res)=>GetAllSortedByCategory(req,res))

module.exports = Router;
