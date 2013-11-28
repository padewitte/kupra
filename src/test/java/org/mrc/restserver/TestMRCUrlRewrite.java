package org.mrc.restserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mrc.restserver.launcher.MRCServerBean;
import org.mrc.restserver.launcher.MRCUrlRewrite;

public class TestMRCUrlRewrite {

	MRCUrlRewrite stressObject;

	@Before
	public void setUp() throws Exception {
		MRCServerBean contentServer = new MRCServerBean("0.0.0.0", 8669, null);
		MRCServerBean documentationServer = new MRCServerBean("0.0.0.0", 8668,
				"documentation");
		List<MRCServerBean> otherServers = new ArrayList<MRCServerBean>();
		otherServers.add(new MRCServerBean("0.0.0.0", 8672, "firstServer"));
		otherServers.add(new MRCServerBean("0.0.0.0", 8672, "secondServer"));
		otherServers.add(new MRCServerBean("0.0.0.0", 8673, "thirdServer"));
		stressObject = new MRCUrlRewrite(contentServer, documentationServer,
				otherServers);
	}

	@Test
	public void testRewrite() {
		try {
			//Documentation server test
			assertEquals("http://127.0.0.1:8668/",stressObject.rewrite("http://127.0.0.1:8670/documentation",
					"/documentation", null));
			assertEquals("http://127.0.0.1:8668/",stressObject.rewrite("http://127.0.0.1:8670/documentation/",
					"/documentation/", null));
			assertEquals("http://127.0.0.1:8668/index.html",stressObject.rewrite("http://127.0.0.1:8670/documentation/index.html",
					"/documentation/index.html", null));
			
			//Content server test
			assertEquals("http://127.0.0.1:8669",stressObject.rewrite("http://127.0.0.1:8670",
					"", null));
			assertEquals("http://127.0.0.1:8669/",stressObject.rewrite("http://127.0.0.1:8670/",
					"/", null));
			assertEquals("http://127.0.0.1:8669/dir/res.js",stressObject.rewrite("http://127.0.0.1:8670/dir/res.js",
					"/dir/res.js", null));
			
			//REST Servers test
			assertEquals("http://127.0.0.1:8672/firstServer/test",stressObject.rewrite("http://127.0.0.1:8670/firstServer/test",
					"/firstServer/test", null));
			assertEquals("http://127.0.0.1:8672/firstServer/test/searchId",stressObject.rewrite("http://127.0.0.1:8670/firstServer/test/searchId",
					"/firstServer/test/searchId", null));
			assertEquals("http://127.0.0.1:8672/secondServer/test",stressObject.rewrite("http://127.0.0.1:8670/secondServer/test",
					"/secondServer/test", null));
			assertEquals("http://127.0.0.1:8673/thirdServer/test",stressObject.rewrite("http://127.0.0.1:8670/thirdServer/test",
					"/thirdServer/test", null));
			
		} catch (Exception e) {
			fail("Something wrond append");
			e.printStackTrace();
		}
	}

}
