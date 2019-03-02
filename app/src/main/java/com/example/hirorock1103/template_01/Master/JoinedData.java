package com.example.hirorock1103.template_01.Master;

public class JoinedData {


    public static class GroupCount{

        private int count;
        private String groupName;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
    }

    public static class GroupTipsContents{

        private String groupName;
        private String tipsTitle;
        private String tipsType;
        private String tipsContents;
        private String tipsMoviePath;
        private byte[] tipsImage;
        private int groupId;
        private int tipsId;


        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getTipsTitle() {
            return tipsTitle;
        }

        public void setTipsTitle(String tipsTitle) {
            this.tipsTitle = tipsTitle;
        }

        public String getTipsType() {
            return tipsType;
        }

        public void setTipsType(String tipsType) {
            this.tipsType = tipsType;
        }

        public String getTipsContents() {
            return tipsContents;
        }

        public void setTipsContents(String tipsContents) {
            this.tipsContents = tipsContents;
        }

        public String getTipsMoviePath() {
            return tipsMoviePath;
        }

        public void setTipsMoviePath(String tipsMoviePath) {
            this.tipsMoviePath = tipsMoviePath;
        }

        public byte[] getTipsImage() {
            return tipsImage;
        }

        public void setTipsImage(byte[] tipsImage) {
            this.tipsImage = tipsImage;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getTipsId() {
            return tipsId;
        }

        public void setTipsId(int tipsId) {
            this.tipsId = tipsId;
        }
    }


}
