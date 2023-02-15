/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author PC
 */
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

import base.ConnexionPOSTGRES;

public class GenericDAO {
    String prefixe = "OB";
    int longPK = 7;
    String nomFonction = "getSeqPRS";
    
    public String construirePK(Connection c) throws SQLException{
        Boolean mine = true;
        if(c==null || c.isClosed()){ 
            c = ConnexionPOSTGRES.enterToBdd();
            mine = false;
        }
        int sequence = this.getSequence(c);
        String pk = this.getPrefixe();
        int nb = this.getPrefixe().length();
        int reste = this.getLongPK() - nb;
        String num = this.mameno(sequence, reste);
        if(mine == false) c.close();
        return this.getPrefixe()+num;
    }
    public String mameno(int numero, int reste){
        String num = numero+"";
        String zero = "";
        reste = reste - num.length();
        for (int i = 0; i < reste; i++) {
            zero = zero +"0";
        }
        String retour = zero + num;
        return retour;
    }
    public int getSequence(Connection c) throws SQLException{
        String sql = "SELECT nextval('"+this.getNomFonction()+"')";
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    boolean correspondanceTable(Class<?> modelClass){
        if(modelClass.getDeclaredAnnotation(Correspondance.class)==null)
            return false;
        return true;
    }

    int correspondanceColonne(Field[] modelFields){
        int val=0;
        for (int i = 0; i < modelFields.length; i++) {
            if(modelFields[i].getDeclaredAnnotation(Correspondance.class)!=null)
                val++;
        }
        return val;
    }

     boolean correspondanceAll(Class<?> modelClass,Field[] modelFields){
        if(this.correspondanceTable(modelClass)==true && this.correspondanceColonne(modelFields)!=0)
            return true;
        return false;
    }

     String nomTable(Class<?> modelClass){
        Correspondance corespTable=modelClass.getAnnotation(Correspondance.class);
        String table=corespTable.nomTable();
        if(table.compareTo("")==0)
            return modelClass.getName();
        return table;
    }

     String nomColonnePrimaryKey(Field[] modelFields)throws Exception{
        for (int i = 0; i < modelFields.length; i++) {
            if(modelFields[i].getDeclaredAnnotation(Correspondance.class)==null)
                continue;
            Correspondance coresp=modelFields[i].getAnnotation(Correspondance.class);
            if(coresp.primarykey()==false)
                continue;
            String val=coresp.nomColonne();
            if(val.compareTo("")==0)
                val=modelFields[i].getName();
            return val;
        }
        throw new Exception("pas de primary key");
    }

     String valeurPrimaryKey(Field[] modelFields)throws Exception{
        for (int i = 0; i < modelFields.length; i++) {
            if(modelFields[i].getDeclaredAnnotation(Correspondance.class)==null)
                continue;
            Correspondance coresp=modelFields[i].getAnnotation(Correspondance.class);
            if(coresp.primarykey()==false)
                continue;
            char[] nomfield=modelFields[i].getName().toCharArray();
            nomfield[0]=Character.toUpperCase(nomfield[0]);
            String nomFonction=new String(nomfield);
            Method met=this.getClass().getDeclaredMethod("get"+nomFonction);
            String val=met.invoke(this).toString();
            return val;
        }
        throw new Exception("pas de valeur de primary key");
    }

     String[] nomColonne(Field[] modelFields){
        String [] val=new String[this.correspondanceColonne(modelFields)];
        int indice=0;
        for (int i = 0; i < modelFields.length; i++) {
            if(modelFields[i].getDeclaredAnnotation(Correspondance.class)==null)
                continue;
            Correspondance coresp=modelFields[i].getAnnotation(Correspondance.class);
            val[indice]=coresp.nomColonne();
            if(val[indice].compareTo("")==0)
                val[indice]=modelFields[i].getName();
            indice++;
        }
        return val;
    }

     String[] valeur(Field[] modelFields)throws Exception{
        String [] val=new String[this.correspondanceColonne(modelFields)];
        int indice=0;
        for (int i = 0; i < modelFields.length; i++) {
            if(modelFields[i].getDeclaredAnnotation(Correspondance.class)==null)
                continue;
            char[] nomfield=modelFields[i].getName().toCharArray();
            nomfield[0]=Character.toUpperCase(nomfield[0]);
            String nomFonction=new String(nomfield);
            Method met=this.getClass().getDeclaredMethod("get"+nomFonction);
            try {
                val[indice] = met.invoke(this).toString();
            }
            catch (NullPointerException e){
                val[indice] = "";
            }
            indice++;
        }
        return val;
    }

     Method getMethodset(Method[] met,Field modelFields,Object obj)throws Exception{
        char[] nomfield=modelFields.getName().toCharArray();
        nomfield[0]=Character.toUpperCase(nomfield[0]);
        String nomFonction=new String(nomfield);
        for (int j = 0; j < met.length; j++) {
            if(met[j].getName().endsWith(nomFonction)==true && met[j].getName().startsWith("set")==true){
                // System.out.println(met[j].toString());
                return met[j];
            }
        }
        throw new Exception("pas de fonction appeler set"+nomFonction);
    }

    //obj instance
    //valeur vector de tableau d'objet
     void setValeur(Field[] modelFields,Object[] obj,Vector valeur )throws Exception{
        Method[] met=null;
        try {
            met = obj[0].getClass().getDeclaredMethods();
        }
        catch (Exception e){
            throw new Exception("pas de valeur");
        }
        for (int j = 0; j < obj.length; j++) {
            Object[] base=(Object[])valeur.get(j);
            for (int i = 0; i < base.length; i++) {
                if(base[i]==null)
                    continue;
                Method m=this.getMethodset(met,modelFields[i], obj[j]);
//                System.out.println(m.toString()+" "+base[i].getClass().toString());
                m.invoke(obj[j], base[i]);
            }
        }
    }

     String createStringDelete(String table,String id,String where){
        String val="delete from "+table;
        val=val+" where "+id+" ='"+where+"'";
        return val;
    }

     String createStringInsert(String table,String[] colonne, String[] valeur){
         String val="insert into "+table+"(";
         Vector indiceNull=new Vector<>();
         for (int i = 0; i < valeur.length; i++) {
             if(i==0 && valeur[i].compareTo("0")==0){
                 indiceNull.add(true);
                 continue;
             }
             if(valeur[i]==null || valeur[i].compareTo("")==0){
                 indiceNull.add(true);
                 continue;
             }
             indiceNull.add(false);
         }
         boolean virgule=false;

         for (int i = 0; i < colonne.length; i++) {
             if((Boolean)indiceNull.get(i)==true)
                 continue;
             if(virgule==true){
                 val=val+",";
             }
             val=val+colonne[i];
             virgule=true;
         }
         virgule=false;
         val=val+") values(";
         for (int i = 0; i < valeur.length; i++) {
             if((Boolean)indiceNull.get(i)==true)
                 continue;
             if(virgule==true)
                 val=val+",";
             val=val+"'"+valeur[i]+"'";
             virgule=true;
         }
         val=val+")";
         return val;
    }

     String createStringFindAll(String table,String[] colonne){
        String val="select ";
        for (int i = 0; i < colonne.length; i++) {
            if(i!=0)
                val=val+",";
            val=val+colonne[i];
        }
        val=val+" from "+table;
        return val;
    }

     String createStringFindById(String table,String[] colonne,String id,String referenceColonne){
        String val="select ";
        for (int i = 0; i < colonne.length; i++) {
            if(i!=0)
                val=val+",";
            val=val+colonne[i];
        }
        val=val+" from "+table+" where "+referenceColonne+"='"+id+"'";
        return val;
    }

     String createStringUpdate(String table,String[] colonne,String[] valeur,String id,String where){
        String val="update "+table+" set ";
        for (int i = 0; i < colonne.length; i++) {
            if(i!=0)
                val=val+",";
            val=val+colonne[i]+"='"+valeur[i]+"'";
        }
        val=val+" where "+id+" ='"+where+"'";
        return val;
    }

    String createStringFindBy(String table,String[] colonne,String[] valeur,String id,String where){
        String val="select ";
        for (int i = 0; i < colonne.length; i++) {
            if(i!=0)
                val=val+",";
            val=val+colonne[i];
        }
        val=val+" from "+table+" where ";
        int test=0;
        for (int i = 0; i < valeur.length; i++) {
            if(valeur[i]==null || valeur[i].compareTo("")==0 || valeur[i].compareTo("0")==0 || valeur[i].compareTo("0.0")==0)
                continue;
            if(test!=0)
                val=val+"and ";
            val=val+colonne[i]+"='"+valeur[i]+"'";
            test=1;
        }
        return val;
    }

    public  void save(Connection connex)throws Exception{
        Boolean mine = true;
        if(connex == null || connex.isClosed()){
            connex = ConnexionPOSTGRES.enterToBdd();
            mine = false;
        }
        Statement espace=null;
        try{
            Class<?> modelClass=this.getClass();
            Field [] modelFields=modelClass.getDeclaredFields();
            if(this.correspondanceAll(modelClass, modelFields)==false)
                throw new Exception("pas de correspondance");
            
            String table=this.nomTable(modelClass);
            String[] colonne=this.nomColonne(modelFields);
            String[] valeur=this.valeur(modelFields);

            String requete=this.createStringInsert(table, colonne, valeur);
//            System.out.println(requete);
            espace=connex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int res=espace.executeUpdate(requete);
            connex.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally{
            if(espace!=null)
                espace.close();
            if(mine == false) connex.close();
        }
    }

    public  Object[] findAll(Connection connex)throws Exception{
        Boolean mine = true;
        if(connex == null || connex.isClosed()){
            connex = ConnexionPOSTGRES.enterToBdd();
            mine = false;
        }
        Statement espace=null;
        ResultSet res=null;
        try {
            Class<?> modelClass=this.getClass();
            Field [] modelFields=modelClass.getDeclaredFields();
            if(this.correspondanceAll(modelClass, modelFields)==false)
                throw new Exception("pas de correspondance");

            String table=this.nomTable(modelClass);
            String[] colonne=this.nomColonne(modelFields);

            String requete=this.createStringFindAll(table, colonne);
            espace=connex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            res=espace.executeQuery(requete);

            // get valeur base
            Vector valeur=new Vector();
            while(res.next()){
                Object[] s=new Object[colonne.length];
                for (int i = 0; i < s.length; i++) {
                    s[i]=res.getObject(i+1);
                }
                valeur.add(s);
            }

            Object[] o=this.toObject(valeur, modelClass, modelFields);

            return o;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally{
            if(res!=null)
                res.close();
            if(espace!=null)
                espace.close();
            if(mine == false) connex.close();
        }
    }
    public Vector objects(Connection c,String sql) throws SQLException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        //System.out.println(query);
        if(c==null || c.isClosed()){ c = ConnexionPOSTGRES.enterToBdd(); }
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Field[] fields = this.getClass().getDeclaredFields();
        Vector vObjects = new Vector<>();
        System.out.println(sql);
        while(resultSet.next()){
            Constructor<?> constructor = this.getClass().getDeclaredConstructor();
            Object ob = constructor.newInstance();
            try{
                int i = 1;
                for(Field field : fields){
                    field.setAccessible(true);
                    String typeF = field.getType()+""; 
                    if(typeF.contains("float")){
                        field.set(ob,resultSet.getFloat(i));
                    }
                    else if(typeF.contains("int")){
                        field.set(ob,resultSet.getInt(i));
                    }
                    else if(typeF.contains("String")){
                        field.set(ob,resultSet.getString(i));
                    }
                    else if(typeF.contains("Date")){
                        field.set(ob,resultSet.getDate(i));
                    }
                    else if(typeF.contains("Vector")){
                    }
                    else if(typeF.contains("Statistique")){
                    }
                    else if(typeF.contains("long")){
                        field.set(ob, resultSet.getLong(i));   
                    }
                    else if(typeF.contains("PossessionTeam")){
                    }
                    else{
                        field.set(ob, resultSet.getObject(i));
                    }
                    
                    i += 1;
                }
            }
            catch(Exception e){
                System.out.println("err");
                System.out.println(e);
            }
            vObjects.add(ob);
        }   
        c.close();   
        return vObjects;   
    }
    public Object[] findBy(Connection connex)throws Exception{
        Boolean mine = true;
        if(connex == null || connex.isClosed()){
            connex = ConnexionPOSTGRES.enterToBdd();
            mine = false;
        }
        Statement espace=null;
        ResultSet res=null;
        try{
            Class<?> modelClass=this.getClass();
            Field [] modelFields=modelClass.getDeclaredFields();
            if(this.correspondanceAll(modelClass, modelFields)==false)
                throw new Exception("pas de correspondance");
            
            String table=this.nomTable(modelClass);
            String[] colonne=this.nomColonne(modelFields);
            String[] valeur=this.valeur(modelFields);
            String id=this.nomColonnePrimaryKey(modelFields);
            String where=this.valeurPrimaryKey(modelFields);

            String requete=this.createStringFindBy(table, colonne, valeur,id,where);
//            System.out.println(requete);
            espace=connex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            res=espace.executeQuery(requete);

            // get valeur base
            Vector  val=new Vector();
            while(res.next()){
                Object[] s=new Object[colonne.length];
                for (int i = 0; i < s.length; i++) {
                    s[i]=res.getObject(i+1);
                }
                val.add(s);

            }

            Object[] o=this.toObject(val, modelClass, modelFields);
//            System.out.println(o.length);
            return o;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally{
            if(espace!=null)
                espace.close();
            if(mine == false) connex.close();
        }
    }

    public  void update(Connection connex)throws Exception{
        Boolean mine = true;
        if(connex == null || connex.isClosed()){
            connex = ConnexionPOSTGRES.enterToBdd();
            mine = false;
        }
        Statement espace=null;
        try{
            Class<?> modelClass=this.getClass();
            Field [] modelFields=modelClass.getDeclaredFields();
            if(this.correspondanceAll(modelClass, modelFields)==false)
                throw new Exception("pas de correspondance");
            
            String table=this.nomTable(modelClass);
            String[] colonne=this.nomColonne(modelFields);
            String[] valeur=this.valeur(modelFields);
            String id=this.nomColonnePrimaryKey(modelFields);
            String where=this.valeurPrimaryKey(modelFields);

            String requete=this.createStringUpdate(table, colonne, valeur,id,where);
            espace=connex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int res=espace.executeUpdate(requete);
            connex.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally{
            if(espace!=null)
                espace.close();
            if(mine == false) connex.close();    
        }
    }

    public Object findById(Connection connex)throws Exception{
        Boolean mine = true;
        if(connex == null || connex.isClosed()){
            connex = ConnexionPOSTGRES.enterToBdd();
            mine = false;
        }
        Statement espace=null;
        ResultSet res=null;
        try {
            Class<?> modelClass=this.getClass();
            Field [] modelFields=modelClass.getDeclaredFields();
            if(this.correspondanceAll(modelClass, modelFields)==false)
                throw new Exception("pas de correspondance");

            String table=this.nomTable(modelClass);
            String[] colonne=this.nomColonne(modelFields);
            String referenceColonne=this.nomColonnePrimaryKey(modelFields);
            String id=this.valeurPrimaryKey(modelFields);

            String requete=this.createStringFindById(table, colonne,id,referenceColonne);

            espace=connex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            res=espace.executeQuery(requete);

            // get valeur base
            Vector valeur=new Vector();
            while(res.next()){
                Object[] s=new Object[colonne.length];
                for (int i = 0; i < s.length; i++) {
                    s[i]=res.getObject(i+1);
                }
                valeur.add(s);
            }

            Object[] o=this.toObject(valeur, modelClass, modelFields);

            return o[0];

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally{
            if(res!=null)
                res.close();
            if(espace!=null)
                espace.close();
            if(mine == false) connex.close();
        }
        
    }

    public static int sequence(Connection connex,String nom)throws Exception{
        Statement espace=null;
        ResultSet res=null;
        try{

            String requete="select nextval('"+nom+"')";
            espace=connex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            res=espace.executeQuery(requete);
            while (res.next()){
                return res.getInt(1);
            }
            return 0;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally{
            if(res!=null)
                res.close();
            if(espace!=null)
                espace.close();
        }
    }
    

    public  void delete(Connection connex)throws Exception{
        Boolean mine = true;
        if(connex == null || connex.isClosed()){
            connex = ConnexionPOSTGRES.enterToBdd();
            mine = false;
        }
        Statement espace=null;
        try{
            Class<?> modelClass=this.getClass();
            Field [] modelFields=modelClass.getDeclaredFields();
            if(this.correspondanceAll(modelClass, modelFields)==false)
                throw new Exception("pas de correspondance");
            
            String table=this.nomTable(modelClass);
            String id=this.nomColonnePrimaryKey(modelFields);
            String where=this.valeurPrimaryKey(modelFields);

            String requete=this.createStringDelete(table,id,where);
//            System.out.println(requete);
            espace=connex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int res=espace.executeUpdate(requete);
            connex.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally{
            if(espace!=null)
                espace.close();
            if(mine == false) connex.close();
        }
        
    }

     Object[] toObject(Vector valeur,Class<?> modelClass,Field [] modelFields)throws Exception{
        //instance
        Object[] o=new Object[valeur.size()]; 
        for (int i = 0; i < valeur.size(); i++) {
            Constructor<?> c=modelClass.getConstructor();
            o[i]=c.newInstance();
        }
        this.setValeur(modelFields, o,valeur);
//         System.out.println(o.length);
        return o;
    }
    public void setPrefixe(String prefixe) {
        this.prefixe = prefixe;
    }
    public String getPrefixe() {
        return prefixe;
    }
    public void setLongPK(int longPK) {
        this.longPK = longPK;
    }
    public int getLongPK() {
        return longPK;
    }
    public void setNomFonction(String nomFonction) {
        this.nomFonction = nomFonction;
    }
    public String getNomFonction() {
        return nomFonction;
    }
}
