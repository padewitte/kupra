package org.mrc.restserver.launcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Producer;
import org.apache.camel.component.http.UrlRewrite;

/**
 * Rewrites rules when -r flag is set.
 * Very basic. Could be optimized.
 * @author Pierre-Alban DEWITTE
 *
 */
public class MRCUrlRewrite implements UrlRewrite {

	private Map<Pattern, String> rewriteRules = new HashMap<Pattern, String>();
	private Pattern contentPattern;
	private String contentPort;

	public MRCUrlRewrite(MRCServerBean contentServer,MRCServerBean documentationServer,
			List<MRCServerBean> otherServers) {
		
		Pattern documentationRule = Pattern.compile("(https?://[^/:]*)(?::\\d*)?/"+documentationServer.getDefaultContext()+"/?((?:[a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)");
		rewriteRules.put(documentationRule,
				"" + documentationServer.getListenPort()+"/");
		
		for (MRCServerBean server : otherServers) {
			rewriteRules.put(Pattern.compile("(https?://[^/:]*)(?::\\d*)?(/"+server.getDefaultContext()+"(?:[a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)")
			,
					"" + server.getListenPort());
		}
		this.contentPattern = Pattern.compile("(https?://[^/:]*)(?::\\d*)?(/?(?:[a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)");
		this.contentPort = ""+contentServer.getListenPort();
		
	}

	public String rewrite(String url, String relativeUrl, Producer producer)
			throws Exception {
		String ret = null;
		Iterator<Entry<Pattern, String>> ruleSet = rewriteRules.entrySet().iterator();

		while (ruleSet.hasNext() && ret == null ) {
			Entry<Pattern, String> rule = ruleSet.next();
			Matcher matcher = rule.getKey().matcher(url);
			if(matcher.find()){
				ret = matcher.group(1) + ":"+rule.getValue();
				if(matcher.group(2) != null){
					ret = ret + matcher.group(2);
				}
			}
		}
		if(ret == null){
			Matcher matcher = contentPattern.matcher(url);
			if(matcher.find()){
				ret = matcher.group(1) + ":"+contentPort+ matcher.group(2);
			}else{
				ret = url;
			}
		}
		//URL  http://127.0.0.1:8670 not rewrite
		return ret;
	}

}
