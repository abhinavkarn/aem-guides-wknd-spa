import React from "react";
import { EditableComponent, MapTo } from "@adobe/aem-react-editable-components";
require("./List.scss");

export const ListEditConfig = {
  emptyLabel: "LIST",
  isEmpty: function (props) {
    return !props ;
  },
};

const List = (props) => {
  console.log(props);
  if (ListEditConfig.isEmpty(props)) {
    return null;
  }

  const listItems = props.items.map(listItem =>
      <li><a href={listItem.path}>{listItem.title}</a></li>
  );

  return (
    <main>
        <ol class="gradient-list">
            {listItems}
        </ol>
    </main>
  );
};

const EditableList = (props) => {
  return (
    <EditableComponent config={ListEditConfig} {...props}>
      <List {...props} />
    </EditableComponent>
  );
};

export default MapTo("wknd-spa-react/components/list")(
  EditableList
);
