const Product = require("../models/productModel");
const User = require("../models/UserModel");

const addProduct = async (req, res) => {
  const sid = req.params.sid;
  const seller = await User.findById(sid);
  if (!seller) return res.status(404).send("some error occured");
  const newProd = await Product.create({ ...req.body, sellerId: sid });
  if (!newProd) return res.status(504).send("some error occured");
  let products = await seller.myProducts;
  products = [...products, await newProd._id];
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
  const products = seller.myProducts;
  const idx = products.indexOf(pid);
  if (idx > -1) {
    products.splice(idx, 1);
    User.findByIdAndUpdate(sid, { myProducts: products }, function (e, doc) {
      if (e) return res.status(400).send("error occured");
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
  if (product.sellerId != sid)
    return res.status(401).send("unauthorized access");
  Product.findByIdAndUpdate(pid, { ...req.body }, function (e, doc) {
    if (e) return res.status(404).send("some error occured");
    res.send("successfully updated");
  });
};

const viewMyProducts = async (req, res) => {
  const sid = req.params.sid;
  const categories = req.query.category;
  const tags = req.query.tag;
  let Products = await Product.find({ sellerId: sid });
  if (!Products) return res.status(404).send("not found");
  if (!categories && !tags) return res.json({ ...Products });
  let ans = [];
  for (let i = 0; i < Products.length; i++) {
    const category = Products[i].categories,
      tag = Products[i].tags;
    if (
      category &&
      categories &&
      category.some((i) => categories.includes(i))
    ) {
      ans = [...ans, Products[i]];
      continue;
    }
    if (tags && tag && tag.some((i) => tags.includes(i))) {
      ans = [...ans, Products[i]];
    }
  }
  res.json(ans);
};

const viewOne = async (req, res) => {
  const pid = req.params.pid;
  const product = await Product.findById(pid);
  if (!product) return res.status(404).send("not found");
  res.json(product);
};

const viewall = async (req, res) => {
  const categories = req.query.category;
  const tags = req.query.tag;
  // const sorted=req.query.sorted;
  let Products = await Product.find();
  if (!Products) return res.status(404).send("not found");
  if (!categories && !tags) return res.json({ ...Products });
  let ans = [];
  for (let i = 0; i < Products.length; i++) {
    const category = Products[i].categories,
      tag = Products[i].tags;
    if (
      category &&
      categories &&
      category.some((i) => categories.includes(i))
    ) {
      ans = [...ans, Products[i]];
      continue;
    }
    if (tags && tag && tag.some((i) => tags.includes(i))) {
      ans = [...ans, Products[i]];
    }
  }
  res.json(ans);
};

module.exports = {
  addProduct,
  removeProduct,
  updateProduct,
  viewMyProducts,
  viewall,
  viewOne,
};
