package io.jenkins.plugins.wechat;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.*;
import io.jenkins.plugins.wechat.model.urlData;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;

import io.jenkins.plugins.wechat.model.weChatData;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;

public class WeChatNotifier extends Notifier implements SimpleBuildStep {

    private final String corpid;
    private final String secret;
    private final int agentid;

    private final String touser;
    private final String toparty;
    private final String totag;
    private final String picURL;

    @DataBoundConstructor
    public WeChatNotifier(String corpid, String secret, int agentid, String touser, String toparty, String totag,
                          String picURL) {
        this.corpid = corpid;
        this.secret = secret;
        this.agentid = agentid;

        this.touser = touser;
        this.toparty = toparty;
        this.totag = totag;
        this.picURL = picURL;
    }

    public String getCorpid() {
        return corpid;
    }

    public String getSecret() {
        return secret;
    }

    public int getAgentid() {
        return agentid;
    }

    public String getTouser() {
        return touser;
    }

    public String getToparty() {
        return toparty;
    }

    public String getTag() {
        return totag;
    }

    public String getPicURL() {
        return picURL;
    }


    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {

        // check build result
        Result result = run.getResult();
        boolean unStable = result != null && result.isWorseThan(Result.UNSTABLE);
        if (unStable) {
            listener.getLogger().println("The build " + result.toString() + ", so the news was not send.");
            return;
        }

        listener.getLogger().println("**************************************************************************************************");
        listener.getLogger().println("**************************************************************************************************");
        listener.getLogger().println("********************************          send weChat        ********************************");
        listener.getLogger().println("**************************************************************************************************");
        listener.getLogger().println("**************************************************************************************************");

        WeChatMessage sw = new WeChatMessage();
        try {
            WeChatHelper singleton = WeChatHelper.getInstance();
            Map<String, Object> map = singleton.getAccessTokenAndJsapiTicket(corpid, secret);
            String token = (String) map.get("access_token");

            String postdata = sw.createpostdata(agentid, touser, toparty, totag, contentInfo(run, listener));
            String resp = sw.post("utf-8", WeChatMessage.CONTENT_TYPE, (new urlData()).getSendMessage_Url(), postdata,
                    token);
            listener.getLogger().println("resp:" + resp);

        } catch (Exception e) {
            e.printStackTrace();
            listener.getLogger().println("err:" + e.getMessage());
        }
    }

    private weChatData.NewsBean contentInfo(Run<?, ?> run, TaskListener listener) {
        Map<String, String> envVars = run.getEnvVars();
        weChatData.NewsBean.ArticlesBean articlesBean = new weChatData.NewsBean.ArticlesBean();
        articlesBean.setTitle("爱康_Android");
        articlesBean.setPicurl(getPicURL());
        if (envVars.containsKey("appPgyerURL")) {
            articlesBean.setUrl(envVars.get("appPgyerURL"));
        } else {
            articlesBean.setUrl("https://www.pgyer.com/WftB");
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (envVars.containsKey("buildName")) {
            stringBuilder.append(envVars.get("buildName"));
        }
        if (envVars.containsKey("buildVersion")) {
            stringBuilder.append("-V").append(envVars.get("buildVersion"));
        }
        if (envVars.containsKey("buildUpdated")) {
            stringBuilder.append("打包时间").append(envVars.get("buildUpdated"));
        }
        articlesBean.setDescription(stringBuilder.toString());
        ArrayList<weChatData.NewsBean.ArticlesBean> arrayList = new ArrayList<>();
        arrayList.add(articlesBean);
        weChatData.NewsBean newsBean = new weChatData.NewsBean();
        newsBean.setArticles(arrayList);
        listener.getLogger().println(arrayList.get(0).toString());
        return newsBean;
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Symbol("wechat")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "WeChat Notification By zlk";
        }
    }
}
