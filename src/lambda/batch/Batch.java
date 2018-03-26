package lambda.batch;

import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lambda.batch.logic.AlertaLogic;
import lambda.batch.model.dto.model.AlertaDTO;

public class Batch {
	String payLoad;
	public Batch(String pl) {
		payLoad=pl;
		JsonParser jps= new JsonParser();
		JsonElement jo= jps.parse(payLoad);
		JsonObject je= (JsonObject) jo;
		AlertaDTO al= new AlertaDTO(je.get("id").getAsString(), new Date(je.get("time").getAsString()), je.get("type").getAsInt());
		AlertaLogic alertaLogic= new AlertaLogic();
		alertaLogic.add(al);
	}
}