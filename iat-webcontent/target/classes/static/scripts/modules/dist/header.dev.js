"use strict";

define(["knockout", "text!templates/header.html"], function (ko, htmlString) {
  function Header(params) {
    var self = this;
    self.text = params.text;
    self.fontSize = params.fontSize || '72px';
    self.fontFamily = params.fontFamily || 'coquette';
    self.fontWeight = params.fontWeight || '900';
  }

  return {
    viewModel: Header,
    template: htmlString
  };
});