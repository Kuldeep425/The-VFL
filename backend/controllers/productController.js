const Product = require("../models/productModel");
const User = require("../models/UserModel");

const addProduct = async (req, res) => {
  const sid = req.params.sid;
  const seller = await User.findById(sid);
  if (!seller) return res.status(404).send("some error occured");
  const newProd = await Product.create({ ...req.body });
  if (!newProd) return res.status(504).send("some error occured");
  let products = seller._doc.myProducts;
  products = [...products, newProd._doc.id];
  User.findByIdAndUpdate(sid, { myProducts: products }, function (e, doc) {
    if (e) return res.status(400).send("some error occured");
    res.send("added successfully");
  });
};

const removeProduct = async (req, res) => {
  const sid = req.params.sid,
    pid = req.params.pid;
  const seller = await User.findById(sid);
  if (!seller) return res.status(404).send("some error occured");
  const products = seller._doc.myProducts;
  const idx = products.indexOf(pid);
  if (idx > -1) {
    products.splice(idx, 1);
    User.findByIdAndUpdate(sid, { myProducts: products }, function (e, doc) {
      if (e) return res.status(400).send("error occured");
      res.send("removed successfully");
    });
    Product.findByIdAndDelete(pid, function (e, doc) {
      if (e) return res.status(404).send("product not found");
      res.send("removed successfully");
    });
  }
};

const updateProduct = async (req, res) => {
  const sid = req.params.sid,
    pid = req.params.pid;
  const product = await Product.findById(pid);
  if (!product) return res.status(404).send("some error occured");
  //Check whether the product owner is updating the product
  if (product._doc.sellerId != sid)
    return res.status(401).send("unauthorized access");
  Product.findByIdAndUpdate(pid, { ...req.body }, function (e, doc) {
    if (e) return res.status(404).send("some error occured");
    res.send("successfully updated");
  });
};

const viewMyProducts = async (req, res) => {
  const sid = req.params.sid;
  const Products = await Product.find({ sellerId: sid });
  if (!Products) return res.status(404).send("not found");
  res.json({ ...Products }); //FIXME: check the type of docs..
};

const productsByCategory = async (req, res) => {
  const category = req.params.cat;
  const products = await Product.find({ category: category });
  if (!products) res.status(404).send("not found");
  res.json({ ...products });
};

const productsByTag = async (req, res) => {
  const tag = req.params.tag; //TODO: complete the function for multiple tags
};

module.exports = {
  addProduct,
  removeProduct,
  updateProduct,
  viewMyProducts,
  productsByCategory,
  productsByTag,
};
