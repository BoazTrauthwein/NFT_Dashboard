package application.interfaces;

import java.util.ArrayList;

import application.classes.NFTCollection;

public interface IBuilder {
	void buildData();
	void addOneCollection(String collSymbol);
	void refreshData();
	ArrayList<NFTCollection> getNftCollection();
}
