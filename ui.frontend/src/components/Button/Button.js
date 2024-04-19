import React from "react";
import { EditableComponent, MapTo } from "@adobe/aem-react-editable-components";
require("./Button.scss");

export const ButtonEditConfig = {
  emptyLabel: "CTA",
  isEmpty: function (props) {
    return !props || !props.text || props.text.trim().length < 1;
  },
};

const Button = (props) => {
  if (ButtonEditConfig.isEmpty(props)) {
    return null;
  }
  return (
    <a id={props.id} href={props.link} class="ButtonStyle">{props.text}</a>
  );
};

const EditableButton = (props) => {
  return (
    <EditableComponent config={ButtonEditConfig} {...props}>
      <Button {...props} />
    </EditableComponent>
  );
};

export default MapTo("wknd-spa-react/components/button")(
  EditableButton
);
