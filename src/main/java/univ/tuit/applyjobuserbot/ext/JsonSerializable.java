package univ.tuit.applyjobuserbot.ext;


import univ.tuit.applyjobuserbot.utils.JsonUtil;

public interface JsonSerializable {
    default String toJson() {
        return JsonUtil.toJson(this);
    }

    default String toPrettyJson() {
        return JsonUtil.toPrettyJson(this);
    }
}
