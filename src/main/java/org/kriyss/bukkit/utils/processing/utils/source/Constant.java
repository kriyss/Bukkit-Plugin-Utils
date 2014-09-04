package org.kriyss.bukkit.utils.processing.utils.source;

public class Constant {
    private String name;
    private Class<?> clazz;
    private String value;
    private Visibility visibility;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public static class ConstantBuilder {
        private String name;
        private Class<?> clazz;
        private String value;
        private Visibility visibility;

        private ConstantBuilder() {
        }

        public static ConstantBuilder aConstant() {
            return new ConstantBuilder();
        }

        public ConstantBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ConstantBuilder withClazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public ConstantBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public ConstantBuilder withVisibility(Visibility visibility) {
            this.visibility = visibility;
            return this;
        }

        public Constant build() {
            Constant constant = new Constant();
            constant.setName(name);
            constant.setClazz(clazz);
            constant.setValue(value);
            constant.setVisibility(visibility);
            return constant;
        }
    }
}
