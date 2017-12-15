package org.vgr.store.rdbms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.util.RandomUtil;
import org.vgr.store.io.FileUtil;

public class BTreeIdxTest {
	private static final Logger LOG = LoggerFactory.getLogger(BTreeIdxTest.class);
	private static BTreeIndex bTree=null;
	static String index=FileUtil.getPath("btree.idx");
	@BeforeClass
	public static void init() {
		//FileStore fileStore=new FileStore(index);
		Store store=new MapStore();
		bTree=new BTreeIndex(store,0);
	}
	
	@Test
	public void testInsert() {
		  // HashSet<Integer> keySet=RandomUtil.randomNumsSet(200, 500);
		  //List<Integer> keyList=new ArrayList<>(keySet);
		   Integer[] arr = new Integer[] {2 ,258 ,260 ,5 ,263 ,265 ,266 ,268 ,269 ,16 ,275 ,20 ,21 ,279 ,280 ,26 ,27 ,284 ,30 ,286 ,287 ,32 ,288 ,289 ,34 ,35 ,38 ,295 ,40 ,43 ,44 ,45 ,301 ,303 ,49 ,305 ,306 ,51 ,308 ,55 ,315 ,59 ,316 ,63 ,319 ,64 ,321 ,322 ,327 ,328 ,72 ,74 ,330 ,332 ,78 ,336 ,81 ,341 ,85 ,86 ,345 ,89 ,94 ,351 ,352 ,354 ,98 ,100 ,356 ,357 ,358 ,104 ,105 ,108 ,364 ,111 ,367 ,371 ,117 ,374 ,376 ,377 ,122 ,378 ,123 ,382 ,384 ,132 ,389 ,133 ,134 ,136 ,392 ,139 ,398 ,144 ,403 ,150 ,407 ,152 ,410 ,156 ,413 ,414 ,160 ,416 ,161 ,419 ,164 ,420 ,166 ,423 ,169 ,426 ,428 ,429 ,430 ,431 ,176 ,178 ,436 ,183 ,186 ,442 ,187 ,447 ,195 ,453 ,197 ,199 ,201 ,202 ,204 ,205 ,206 ,207 ,464 ,466 ,467 ,468 ,215 ,216 ,217 ,474 ,219 ,222 ,478 ,224 ,225 ,482 ,228 ,229 ,487 ,231 ,237 ,241 ,242 ,244 ,246 ,247 ,248 ,249 ,250 ,252};
		   //Integer[] arr = new Integer[] {5,7,2,10,12,13,11};
		   List<Integer> keyList=Arrays.asList(arr);

		   LOG.info("Totak keys : "+keyList.size());
		   keyList.forEach(key->System.out.print(key+" ,"));
		   int i=1;
		   for (Integer integer : keyList) {
			     int key=integer;
	    	      int val=i++;
	    	      bTree.insert(key,val);
			}
		   bTree.traverse();
		  }
	@Test
	@Ignore
	public void testTravrse() {
		      bTree.traverse();
		  }
	
	@Test
	@Ignore
	public void testSerach() {
		HashSet<Integer> keySet=RandomUtil.randomNumsSet(20, 10000);
		String searchKeys= keySet.stream().map(num->""+num).collect(Collectors.joining(","));
		System.out.println("Keys : "+searchKeys);
		for (Integer integer : keySet) {
			  BtreeNode result=bTree.search(bTree.root, integer);
			  if(result!=null) {
				  System.out.print("Key : "+ integer +" Page : "+result.getId() +" -> Keys : "+result.keys()+"\n");
			  }
		   }
	}
	
	@AfterClass
	public static void close() {
	//	bTree.close();
	}
	
	
	
}
