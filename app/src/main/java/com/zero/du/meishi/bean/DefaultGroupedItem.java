package com.zero.du.meishi.bean;

import com.kunminx.linkage.bean.BaseGroupedItem;

public class DefaultGroupedItem extends BaseGroupedItem<DefaultGroupedItem.ItemInfo>{
    public DefaultGroupedItem( String header ,boolean isHeader) {
        super(isHeader, header);
    }

    public DefaultGroupedItem(ItemInfo info) {
        super(info);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private String content;

        public ItemInfo(String title, String group) {
            super(title, group);
        }
        public ItemInfo(String title, String group, String content) {
            super(title, group);
            this.content = content;
        }
        public ItemInfo() {
            super(null, null);
        }
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


    }
}
