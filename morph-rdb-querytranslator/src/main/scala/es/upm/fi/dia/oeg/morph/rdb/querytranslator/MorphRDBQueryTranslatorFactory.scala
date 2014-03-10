package es.upm.fi.dia.oeg.morph.rdb.querytranslator

import es.upm.fi.dia.oeg.morph.r2rml.rdb.engine.R2RMLUnfolder
import java.sql.Connection
import es.upm.fi.dia.oeg.morph.base.querytranslator.NameGenerator
import es.upm.fi.dia.oeg.morph.base.querytranslator.MorphBaseAlphaGenerator
import es.upm.fi.dia.oeg.morph.base.querytranslator.MorphBaseBetaGenerator
import es.upm.fi.dia.oeg.morph.base.querytranslator.MorphBaseCondSQLGenerator
import es.upm.fi.dia.oeg.morph.base.querytranslator.MorphBasePRSQLGenerator
import es.upm.dia.fi.oeg.morph.r2rml.model.R2RMLMappingDocument
import es.upm.fi.dia.oeg.morph.base.model.MorphBaseMappingDocument
import es.upm.fi.dia.oeg.morph.base.engine.IQueryTranslator
import es.upm.fi.dia.oeg.morph.base.engine.IQueryTranslatorFactory

class MorphRDBQueryTranslatorFactory extends IQueryTranslatorFactory {
	def createQueryTranslator(abstractMappingDocument:MorphBaseMappingDocument
	    , conn:Connection) : IQueryTranslator = {
		val md = abstractMappingDocument.asInstanceOf[R2RMLMappingDocument];
		//val unfolder = abstractUnfolder.asInstanceOf[R2RMLUnfolder];
		val unfolder = new R2RMLUnfolder(md);
		
		val nameGenerator = new NameGenerator();
		val alphaGenerator:MorphBaseAlphaGenerator = new MorphRDBAlphaGenerator(md, unfolder);
		val betaGenerator:MorphBaseBetaGenerator = new MorphRDBBetaGenerator(md, unfolder);
		val condSQLGenerator:MorphBaseCondSQLGenerator = new MorphRDBCondSQLGenerator(md, unfolder);
		val prSQLGenerator:MorphBasePRSQLGenerator = new MorphRDBPRSQLGenerator(md, unfolder);
	  
		val queryTranslator = new MorphRDBQueryTranslator(nameGenerator
		    , alphaGenerator, betaGenerator, condSQLGenerator, prSQLGenerator);

		if(conn != null) {
			queryTranslator.connection = conn;
		}
		queryTranslator.mappingDocument = md;
		
		queryTranslator;	  
	}
}