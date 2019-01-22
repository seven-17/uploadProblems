package henu.acm.spider;

public class SpiderFactory {

	public static final BaseSpider getSpiderByHDOJ() {
		return new HDOJSpider();
	}

	public static final BaseSpider getSpdierByCodeForces() {
		return new CodeForcesSpider();
	}
}