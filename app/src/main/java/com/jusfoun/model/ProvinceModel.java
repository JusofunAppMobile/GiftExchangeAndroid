package com.jusfoun.model;

import java.util.List;

/**
 * 省市区
 *
 * @时间 2017/8/11
 * @作者 LiuGuangDan
 */

public class ProvinceModel {


    /**
     * id : 11
     * pid : 0
     * name : 北京
     * children : [{"id":"1101","pid":"11","name":"北京市","children":[{"id":"110101","pid":"1101","name":"东城区"},{"id":"110102","pid":"1101","name":"西城区"},{"id":"110105","pid":"1101","name":"朝阳区"},{"id":"110106","pid":"1101","name":"丰台区"},{"id":"110107","pid":"1101","name":"石景山区"},{"id":"110108","pid":"1101","name":"海淀区"},{"id":"110109","pid":"1101","name":"门头沟区"},{"id":"110111","pid":"1101","name":"房山区"},{"id":"110112","pid":"1101","name":"通州区"},{"id":"110113","pid":"1101","name":"顺义区"},{"id":"110114","pid":"1101","name":"昌平区"},{"id":"110115","pid":"1101","name":"大兴区"},{"id":"110116","pid":"1101","name":"怀柔区"},{"id":"110117","pid":"1101","name":"平谷区"},{"id":"110228","pid":"1101","name":"密云县"},{"id":"110229","pid":"1101","name":"延庆县"}]}]
     */

    public String id;
    public String pid;
    public String name;
    public List<ChildrenBeanX> children;

    public static class ChildrenBeanX {
        /**
         * id : 1101
         * pid : 11
         * name : 北京市
         * children : [{"id":"110101","pid":"1101","name":"东城区"},{"id":"110102","pid":"1101","name":"西城区"},{"id":"110105","pid":"1101","name":"朝阳区"},{"id":"110106","pid":"1101","name":"丰台区"},{"id":"110107","pid":"1101","name":"石景山区"},{"id":"110108","pid":"1101","name":"海淀区"},{"id":"110109","pid":"1101","name":"门头沟区"},{"id":"110111","pid":"1101","name":"房山区"},{"id":"110112","pid":"1101","name":"通州区"},{"id":"110113","pid":"1101","name":"顺义区"},{"id":"110114","pid":"1101","name":"昌平区"},{"id":"110115","pid":"1101","name":"大兴区"},{"id":"110116","pid":"1101","name":"怀柔区"},{"id":"110117","pid":"1101","name":"平谷区"},{"id":"110228","pid":"1101","name":"密云县"},{"id":"110229","pid":"1101","name":"延庆县"}]
         */

        public String id;
        public String pid;
        public String name;
        public List<ChildrenBean> children;

        public static class ChildrenBean {
            /**
             * id : 110101
             * pid : 1101
             * name : 东城区
             */

            public String id;
            public String pid;
            public String name;
        }
    }
}
