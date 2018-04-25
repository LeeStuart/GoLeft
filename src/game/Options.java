package game;

import game.Enumerations.ControlValues;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.newdawn.slick.Input;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Options {
	
	//Graphics options variables
	private static ArrayList<int[]> resolutions;
	private int selectRes;
	private boolean fullscreen;
	
	//Controls options variables
	private HashMap<ControlValues, Integer> controls;

	public Options() {
		
		//Get available resolutions
		resolutions = new ArrayList<int[]>();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice display = ge.getDefaultScreenDevice();
		DisplayMode[] availableModes = display.getDisplayModes();
		for (DisplayMode mode: availableModes) {
			int[] res = {mode.getWidth(), mode.getHeight()};
			if (!contains(resolutions, res)) {
				resolutions.add(res);
			}
		}
		
		//If config.xml file exists, then load that
		File f = new File("config.xml");
		if (f.exists()) {
			loadOptions();
			return;
		}
		
		//Default options
		selectRes = resolutions.size() - 1;
		fullscreen = true;
		controls = new HashMap<ControlValues, Integer>();
		controls.put(ControlValues.JUMP, Input.KEY_W);
		controls.put(ControlValues.LEFT, Input.KEY_A);
		controls.put(ControlValues.RIGHT, Input.KEY_D);
		controls.put(ControlValues.DOWN, Input.KEY_S);
		controls.put(ControlValues.PAUSE, Input.KEY_ESCAPE);
		
	}
	
	private void loadOptions() {
		
		try {
			//Load document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse("config.xml");
			doc.getDocumentElement().normalize();
			
			//Load graphics options
			Element elemGraphics = (Element)doc.getElementsByTagName("Graphics").item(0);
			Element elemResolution = (Element)elemGraphics.getElementsByTagName("Resolution").item(0);
			int resolutionWidth = Integer.parseInt(elemResolution.getElementsByTagName("Width").item(0).getTextContent());
			int resolutionHeight = Integer.parseInt(elemResolution.getElementsByTagName("Height").item(0).getTextContent());
			int[] res = {resolutionWidth, resolutionHeight};
			if (contains(resolutions, res)) {
				selectRes = index(resolutions, res);
			}
			fullscreen = Boolean.parseBoolean(elemGraphics.getElementsByTagName("Fullscreen").item(0).getTextContent());

			//Load controls options
			controls = new HashMap<ControlValues, Integer>();
			Element elemControls = (Element)doc.getElementsByTagName("Controls").item(0);
			NodeList nlControls = elemControls.getChildNodes();
			for (int i = 0; i < nlControls.getLength(); i++) {
				Element e = (Element)nlControls.item(i);
				String cv = e.getNodeName();
				int key = Integer.parseInt(e.getTextContent());
				controls.put(ControlValues.valueOf(cv), key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveOptions() {
		
		try {
			//Create document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element root = doc.createElement("Options");
			doc.appendChild(root);
			
			//Version Number
			Element version = doc.createElement("Version_Number");
			version.appendChild(doc.createTextNode(Main.VERSION_NUMBER));
			root.appendChild(version);
			
			//Save graphics options
			Element elemGraphics = doc.createElement("Graphics");
			root.appendChild(elemGraphics);
			Element elemResolution = doc.createElement("Resolution");
			elemGraphics.appendChild(elemResolution);
			Element elemWidth = doc.createElement("Width");
			elemWidth.appendChild(doc.createTextNode("" + getResolutionWidth()));
			elemResolution.appendChild(elemWidth);
			Element elemHeight = doc.createElement("Height");
			elemHeight.appendChild(doc.createTextNode("" + getResolutionHeight()));
			elemResolution.appendChild(elemHeight);
			Element elemFullscreen = doc.createElement("Fullscreen");
			elemFullscreen.appendChild(doc.createTextNode("" + fullscreen));
			elemGraphics.appendChild(elemFullscreen);
			
			//Save controls options
			Element elemControls = doc.createElement("Controls");
			root.appendChild(elemControls);
			for (ControlValues cv: controls.keySet()) {
				Element e = doc.createElement(cv.toString());
				e.appendChild(doc.createTextNode("" + controls.get(cv)));
				elemControls.appendChild(e);
			}
			
			//Save document to file
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource src = new DOMSource(doc);
			File f = new File("config.xml");
			StreamResult result = new StreamResult(f);
			t.transform(src, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean contains(ArrayList<int[]> array, int[] item) {
		
		for (int[] aItem: array) {
			if (aItem[0] == item[0] && aItem[1] == item[1]) {
				return true;
			}
		}
		return false;
		
	}
	
	private int index(ArrayList<int[]> array, int[] item) {
		
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i)[0] == item[0] && array.get(i)[1] == item[1]) {
				return i;
			}
		}
		return array.size() - 1;
		
	}
	
	public int getResolutionWidth() {
		
		return resolutions.get(selectRes)[0];
		
	}
	
	public int getResolutionHeight() {
		
		return resolutions.get(selectRes)[1];
		
	}
	
	public String getStringResolution() {
		
		int[] res = resolutions.get(selectRes);
		return "" + res[0] + "x" + res[1];
		
	}
	
	public void changeResolution(boolean up) {
		
		if (up) {
			if (selectRes + 1 >= resolutions.size()) { 
				selectRes = 0;
			} else {
				selectRes++;
			}
		} else {
			if (selectRes - 1 < 0) {
				selectRes = resolutions.size() - 1;
			} else {
				selectRes--;
			}
		}
		
	}
	
	public boolean getFullscreen() {
		
		return fullscreen;
		
	}
	
	public void changeFullscreen() {
		
		fullscreen = !fullscreen;
		
	}
	
	public int getControls(ControlValues id) {
		
		return controls.get(id);
		
	}
	
	public void setControlKey(ControlValues id, int key) {
		
		boolean dup = false;
		ControlValues dupId = null;
		
		//Check for duplicate key
		for (ControlValues cv : ControlValues.values()) {
			if (controls.get(cv) == key) {
				dup = true;
				dupId = cv;
			}
		}
		
		if (dup) {
			controls.put(dupId, controls.get(id));
		}
		controls.put(id, key);
		
	}

}
