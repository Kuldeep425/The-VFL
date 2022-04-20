const Router = require("express").Router();
const {
  login,
  register,
  confirmEmail,
} = require("../controllers/AuthController");
Router.post("/login", (req, res) => login(req, res));
Router.post("/register", (req, res) => register(req, res));
Router.get("/verify/:code", (req, res) => confirmEmail(req, res));
module.exports = Router;
