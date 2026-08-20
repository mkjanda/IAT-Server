"use strict";

define(["knockout", "text!templates/administration-timeout.js"], function (ko, htmlString) {
  function AdministrationTimeout() {
    var self = this;
    self.httpReferer = sessionState.getItem("HTTP_REFERER");
  }

  return {
    viewModel: AdministrationTimeout,
    template: htmlString
  };
});