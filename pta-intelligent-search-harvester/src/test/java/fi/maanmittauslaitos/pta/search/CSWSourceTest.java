package fi.maanmittauslaitos.pta.search;

import fi.maanmittauslaitos.pta.search.csw.CSWHarvesterSource;
import fi.maanmittauslaitos.pta.search.csw.Harvestable;
import fi.maanmittauslaitos.pta.search.csw.HarvesterInputStream;
import org.apache.commons.io.IOUtils;

public class CSWSourceTest {
	public static void main(String[] args) throws Exception {
		HarvesterSource source = new CSWHarvesterSource();
		source.setBatchSize(10);
		source.setOnlineResource("http://paikkatietohakemisto.fi/geonetwork/srv/en/csw");

		for (Harvestable harvestable : source) {
			HarvesterInputStream is = source.getInputStream(harvestable);
			try {
				IOUtils.copy(is, System.out);
				if (true) return;
			} finally {
				is.close();
			}
		}
	}
}
