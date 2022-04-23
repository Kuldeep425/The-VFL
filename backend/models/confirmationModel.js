const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const tokenSchema = new Schema({
  createdAt: { type: Date, expires: 100, default: Date.now },
  user: { type: String, required: true },
  confirmationToken: {
    type: String,
    required: true,
  },
});

tokenSchema.index({ lastModifiedDate: 1 }, { expireAfterSeconds: 100 });

const Token = mongoose.model("token", tokenSchema);
module.exports = Token;
