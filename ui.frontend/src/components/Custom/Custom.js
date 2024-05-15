import React from "react";
import { EditableComponent, MapTo } from "@adobe/aem-react-editable-components";
import SevenCustom from "./seven-custom.js";
import QiddiyaCustom from "./qiddiya-custom.js";

require("./Custom.scss");

export const CustomEditConfig = {
  emptyLabel: "Custom",

  isEmpty: function (props) {
    return !props || !props.message || props.message.trim().length < 1;
  },
};

const Custom = (props) => {
  if (CustomEditConfig.isEmpty(props)) {
    return null;
  }

  return (
    <div className="CustomComponent">
      {props.brand == "seven" ? <SevenCustom {...props}/> : <QiddiyaCustom {...props}/> }
      {console.log(props)}
      {console.log("Brand --> " + props.brand)}
    </div>
  );
};

const EditableCustom = (props) => {
  return (
    <EditableComponent config={CustomEditConfig} {...props}>
      <Custom {...props} />
    </EditableComponent>
  );
};

export default MapTo("wknd-spa-react/components/custom-component")(
  EditableCustom
);
