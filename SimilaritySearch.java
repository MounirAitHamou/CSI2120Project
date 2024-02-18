/*
 * CSI 2120 - Programming Paradigms [A]
 * 
 * Mounir Aït Hamou  -  300296173
 * Aroha Upreti      -  300283790
 * 
 */

import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SimilaritySearch {

	public static void main(String[] args) {
		ColorImage queryImage = new ColorImage("queryImages/" + args[0]);
		ColorHistogram queryHist = new ColorHistogram(3);
		queryHist.setImage(queryImage);

		class HistComparator implements Comparator<Entry> {
			private ColorHistogram queryHist;

			public HistComparator(ColorHistogram queryHist) {
				this.queryHist = queryHist;
			}

			public int compare(Entry entry1, Entry entry2) {
				return Double.compare(queryHist.compare(entry1.getHistogram()),
						queryHist.compare(entry2.getHistogram()));

			}
		}

		File[] imageDataset = new File(args[1]).listFiles();
		PriorityQueue<Entry> pq = new PriorityQueue<>(5, new HistComparator(queryHist));

		for (int i = 0; i < imageDataset.length; i++) {
			if (imageDataset[i].getName().endsWith(".txt")) {
				ColorHistogram imageHist = new ColorHistogram(args[1] + "/" + imageDataset[i].getName());
				Entry entry = new Entry(imageHist, imageDataset[i].getName());
				pq.offer(entry);
				if (pq.size() > 5) {
					pq.poll();
				}
			}
		}
		String result = "The 5 most similar images to " + args[0] + " are:";
		while (!pq.isEmpty()) {
			Entry entry = pq.poll();
			result += "\n" + entry.getFilename();
		}
		System.out.println(result);
	}

	static class Entry {
		private ColorHistogram histogram;
		private String filename;

		public Entry(ColorHistogram histogram, String filename) {
			this.histogram = histogram;
			this.filename = filename;
		}

		public ColorHistogram getHistogram() {
			return histogram;
		}

		public String getFilename() {
			return filename;
		}
	}
}