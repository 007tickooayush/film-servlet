<%@page import="java.util.*" %>
<%@page import="src.main.model.Film" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Advanced Movie Management System</title>
	<link href = "https://cdnjs.cloudflare.com/ajax/libs/extjs/6.0.0/classic/theme-triton/resources/theme-triton-all.css"
          rel = "stylesheet" />
    <script type = "text/javascript"
            src = "https://cdnjs.cloudflare.com/ajax/libs/extjs/6.0.0/ext-all.js"></script>
<script type = "text/javascript">
        
        Ext.define('MovieModel',{
            extend:'Ext.data.Model',
            fields:['film_id','title','description','release_year','rating','director','special_features'],
        });    

        Ext.onReady(function(){

            // [IMP] if proxy not working
            // getting the data model to display from server attribute
            // let movieData = <%=request.getAttribute("data")%>;

            let size = 5;

            let rootURL = 'http://localhost:8080/film-servlet/';

            let movieStore = Ext.create('Ext.data.Store',{
                model:'MovieModel',
                autoLoad:{
                    start:0,
                    limit:size,
                },
                pageSize:size,
                remoteSort:true,
                proxy:{
                    type:'rest',
                    enablePaging:true,
                    actionMethods:{
                        read:'GET'
                    },
                    url:rootURL+'pagedjson',
                    reader:{
                        type:'json',
                        root:'data',
                        totalProperty:'total',
                        idProperty:'film_id'
                    }
                }
            });


            let languages = Ext.create('Ext.data.Store',{
                fields: ['language_id','language'],
                data:[
                        {
                            'language_id':1,
                            'language':'English'
                        },
                        {
                            'language_id':2,
                            'language':'Italian'
                        },
                        {
                            'language_id':3,
                            'language':'Japanese'
                        },
                        {
                            'language_id':4,
                            'language':'Mandarin'
                        },
                        {
                            'language_id':5,
                            'language':'French'
                        },
                        {
                            'language_id':6,
                            'language':'German'
                        },
                ]
            });
        
            let rating = Ext.create('Ext.data.Store',{
                data:[
                        {
                            'rating':'G'
                        },
                        {
                            'rating':'PG'
                        },
                        {
                            'rating':'PG-13'
                        },
                        {
                            'rating':'R'
                        },
                        {
                            'rating':'NC-17'
                        }
                ]
            });
        
            let features = Ext.create('Ext.data.Store',{
                fields:['feature_id','features'],
                data:[
                        {
                            'feature_id':1,
                            'feature':'Trailers'
                        },
                        {
                            'feature_id':2,
                            'feature':'Commentaries'
                        },
                        {
                            'feature_id':3,
                            'feature':'Trailers,Commentaries'
                        },
                        {
                            'feature_id':4,
                            'feature':'Deleted Scenes'
                        },
                        {
                            'feature_id':5,
                            'feature':'Trailers,Deleted Scenes'
                        },
                        {
                            'feature_id':6,
                            'feature':'Commentaries,Deleted Scenes'
                        },
                        {
                            'feature_id':7,
                            'feature':'Trailers,Commentaries,Deleted Scenes'
                        },
                        {
                            'feature_id':8,
                            'feature':'Behind the Scenes'
                        },
                        {
                            'feature_id':9,
                            'feature':'Trailers,Behind the Scenes'
                        },
                        {
                            'feature_id':10,
                            'feature':'Commentaries,Behind the Scenes'
                        },
                        {
                            'feature_id':11,
                            'feature':'Trailers,Commentaries,Behind the Scenes'
                        },
                        {
                            'feature_id':12,
                            'feature':'Deleted Scenes,Behind the Scenes'
                        },
                        {
                            'feature_id':13,
                            'feature':'Trailers,Deleted Scenes,Behind the Scenes'
                        },
                        {
                            'feature_id':14,
                            'feature':'Commentaries,Deleted Scenes,Behind the Scenes'
                        },
                        {
                            'feature_id':15,
                            'feature':'Trailers,Commentaries,Deleted Scenes,Behind the Scenes'
                        }
                ]
            });
            
            let searchForm = Ext.create('Ext.form.Panel',{
                title:'Movie Advanced Search',
                id:'movieForm',
                items:[
                    {
                        layout:{
                            type:'hbox',
                            align:'stretch',
                            pack:'center'
                        },
                        xtype:'container',
                        margin:16,
                        items:[
                            {
                                id:'search_title',
                                name:'search_title',
                                xtype:'textfield',
                                fieldLabel:'Movie Name',
                                margin:16
                            },
                            {
                                id:'search_director',
                                name:'search_director',
                                xtype:'textfield',
                                fieldLabel:'Director Name',        
                                margin:16
                            }
                        ]
                            
                    },
                    {
                        layout:{
                            type:'hbox',
                            align:'stretch',
                            pack:'center'
                        },
                        xtype:'container',
                        margin:16,
                        items:[
                            {
                                id:'search_release_year',
                                name:'search_release_year',
                                xtype:'datefield',
                                fieldLabel:'Release Date',
                                margin:16
                            },
                            {
                                id:'search_language',
                                name:'search_language',
                                xtype:'combobox',
                                fieldLabel:'Language',
                                store:languages,
                                valueField:'language_id',
                                displayField:'language',
                                margin:16
                            }       
                        ]
                        
                    },
                    
                ],
                buttons:[
                    {
                        text:'Reset',
                        handler: function(){
                            this.up('form').getForm().reset();
                        }
                },
                {
                        text:'Search',
                        formBind:true,
                        disabled:true,
                        handler:function(){
                            let f = this.up('form').getForm();
                            let searchData = f.getValues();

                            console.warn('Form values: ',searchData);

                            Ext.Ajax.request({
                                    url:'search',
                                    method:'GET',
                                    jsonData:true,
                                    params:{
                                        title:searchData.search_title,
                                        description:searchData.search_description,
                                        language_id:searchData.search_language,
                                        release_year:searchData.search_release_year,
                                        director:searchData.search_director
                                    },
                                    success:function(response){
                                        console.log(response);
                                        // console.log(data);
                                        let jsonResponse = JSON.parse(response.responseText);
                                        console.warn('RESPONSE [JSON]',jsonResponse);
                                        movieStore.loadRawData(jsonResponse);
                                    },
                                    failure:function(response){
                                        console.warn('ERROR: ',response);
                                    }
                                });

                            if(f.isValid()){


                                // f.submit({
                                //     success:function(form,action){
                                //     // 	api
                                //     },
                                //         failure:function(form,action){
                                //     // 	failure log
                                //     }
                                // })
                            }
                        }
                }
                ],
                buttonAlign:'center'
            });

            let addForm = Ext.create('Ext.form.Panel',{
                id:'addForm',
                defaultType:'textfield',
                items:[
                    {
                        name:'title',
                        id:'addTitle',
                        xtype:'textfield',
                        fieldLabel:'Title',
                        allowBlank:false
                    },
                    {
                        id:'release_year',
                        name:'release_year',
                        xtype:'numberfield',
                        fieldLabel:'Release Year',
                        allowBlank:false
                    },
                    {
                        id:'rental_duration',
                        name:'rental_duration',
                        xtype:'numberfield',
                        fieldLabel:'Rental Duration',
                        allowBlank:false
                    },
                    {
                        id:'rental_rate',
                        name:'rental_rate',
                        xtype:'numberfield',
                        fieldLabel:'Rental Rate',
                        allowBlank:false
                    },
                    {
                        id:'replacement_cost',
                        name:'replacement_cost',
                        xtype:'numberfield',
                        fieldLabel:'Replacement Cost',
                    },
                    {
                        id:'special_features',
                        name:'special_features',
                        xtype:'combobox',
                        fieldLabel:'Special Features',
                        store:features,
                        displayField:'feature',
                        valueField:'feature_id',
                        allowBlank:false
                    },
                    {
                        id:'rating',
                        name:'rating',
                        xtype:'combobox',
                        fieldLabel:'Rating',
                        store:rating,
                        displayField:'rating',
                    },
                    {
                        id:'language',
                        name:'language',
                        xtype:'combobox',
                        fieldLabel:'Language',
                        store:languages,
                        valueField:'language_id',
                        displayField:'language',
                    },
                    {
                        id:'length',
                        name:'length',
                        xtype:'numberfield',
                        fieldLabel:'Length',
                    },
                    {
                        id:'director',
                        name:'director',
                        xtype:'textfield',
                        fieldLabel:'Director Name',
                    },
                    {
                        id:'description',
                        name:'description',
                        xtype:'textarea',
                        fieldLabel:'Description',
                        allowBlank:false
                    }
                ],
                buttons:[
                    {
                        text:'Save',
                        formBind:true,
                        handler:function(){
                            var form = this.up('form').getForm()
                            var data = form.getValues();
                            // console.log(form);
                            // console.warn("Form Data: ",data);

                            if(data.title!=='' ||
                                data.release_year !== ''||
                                data.language !== ''||
                                data.description !== ''||
                                data.rating !== '' ||
                                data.special_features!== ''
                            ){
                                console.log('Valid data');

                                Ext.Ajax.request({
                                    url:'add',
                                    method:'POST',
                                    jsonData:true,
                                    params:{
                                        title:data.title,
                                        description:data.description,
                                        language_id:data.language,
                                        length:data.length,
                                        rating:data.rating,
                                        release_year:data.release_year,
                                        rental_duration:data.rental_duration,
                                        rental_rate:data.rental_rate,
                                        replacement_cost:data.replacement_cost,
                                        special_features:data.special_features,
                                        director:data.director
                                    },
                                    success:function(response){
                                        console.log(response);
                                        // console.log(data);
                                        
                                    },
                                    failure:function(response){
                                        console.warn('ERROR: ',response);
                                    }
                                });
                                form.reset();
                                // addWin.close();
                            }else{
                                console.log('Invalid data');
                            }

                        }
                    },
                    {
                        text:'Cancel',
                        handler:function(){
                            addWin.hide();
                        }
                    }
                ],
                buttonAlign:'center'
            });

            
            let addWin = new Ext.Window({
                title:'Add Movie',
                layout:'form',
                id:'addWin',
                width: 400,
                closeAction:'close',
                plain:true,
                items:[
                    addForm
                ]
            });

            // increased the attributes add pending fields 
            let editForm = Ext.create('Ext.form.Panel',{
                id:'editForm',
                items:[
                    {
                        name:'title',
                        id:'eTitle',
                        xtype:'textfield',
                        fieldLabel:'Title',
                        
                    },
                    {
                        id:'release_year',
                        name:'release_year',
                        xtype:'numberfield',
                        fieldLabel:'Release Year',
                    },
                    {
                        id:'rental_duration',
                        name:'rental_duration',
                        xtype:'numberfield',
                        fieldLabel:'Rental Duration',
                    },
                    {
                        id:'rental_rate',
                        name:'rental_rate',
                        xtype:'numberfield',
                        fieldLabel:'Rental Rate',
                    },
                    {
                        id:'replacement_cost',
                        name:'replacement_cost',
                        xtype:'numberfield',
                        fieldLabel:'Replacement Cost',
                    },
                    {
                        id:'special_features',
                        name:'special_features',
                        xtype:'combobox',
                        fieldLabel:'Special Features',
                        store:features,
                        displayField:'feature',
                        valueField:'feature_id'
                    },
                    {
                        id:'rating',
                        name:'rating',
                        xtype:'combobox',
                        fieldLabel:'Rating',
                        store:rating,
                        displayField:'rating',
                    },
                    {
                        id:'language',
                        name:'language',
                        xtype:'combobox',
                        fieldLabel:'Language',
                        store:languages,
                        valueField:'language_id',
                        displayField:'language',
                    },
                    {
                        id:'length',
                        name:'length',
                        xtype:'numberfield',
                        fieldLabel:'Length',
                    },
                    {
                        id:'director',
                        name:'director',
                        xtype:'textfield',
                        fieldLabel:'Director Name',
                    },
                    {
                        id:'description',
                        name:'description',
                        xtype:'textarea',
                        fieldLabel:'Description',
                        
                    }
                ],
                buttons:[
                    {
                        text:'Save',
                        handler:function(){
                            // grid row data
                            let sel = Ext.getCmp('movieGrid').getSelectionModel().getSelection();
                            let gridData = sel[0].data;

                            var data = this.up('form').getForm().getValues();
                            console.warn("Form Data: ",data);
                            // console.warn('film_id: ',gridData.film_id );
                            Ext.Ajax.request({
                                    url:'edit',
                                    method:'PUT',
                                    jsonData:true,
                                    params:{
                                        film_id:gridData.film_id,
                                        title:data.title,
                                        description:data.description,
                                        language_id:data.language,
                                        length:data.length,
                                        rating:data.rating,
                                        release_year:data.release_year,
                                        rental_duration:data.rental_duration,
                                        rental_rate:data.rental_rate,
                                        replacement_cost:data.replacement_cost,
                                        special_features:data.special_features,
                                        director:data.director
                                    },
                                    success:function(response){
                                        console.log(response);
                                        console.log(data);
                                        Ext.getCmp('movieGrid').getView().refresh();
                                    },
                                    failure:function(response){
                                        console.warn('ERROR: ',response);
                                    }
                            });
                        }
                        
                    },
                    {
                        text:'Cancel',
                        handler:function(){
                            editWin.hide();
                        }
                    }
                ],
                buttonAlign:'center'
            });
            
            let editWin = new Ext.Window({
                title:'Edit Movie',
                layout:'form',
                id:'editWin',
                width: 400,
                closeAction:'close',
                plain:true,
                items:[
                        editForm
                    ]
            });
        
            let addBtn = new Ext.create('Ext.Button',{
                text:'Add',
                iconCls:'fa fa-plus',
                listeners:{
                    click:function(){
                        addWin.show();
                    }
                }
            });
            
            let editBtn = new Ext.create('Ext.Button',{
        	text:'Edit',
        	iconCls:'fa fa-edit',
            listeners:{
            	click:function(){
                        let sel = Ext.getCmp('movieGrid').getSelectionModel().getSelection();
                        
                        if(sel.length === 0 || sel.length>1)
                            Ext.Msg.alert('Invalid Selection','Select only and at least one row to edit');
                        else{
                            let selected = sel[0].data;
                            console.warn('grid row data',selected);
                            // console.log(editForm.items.items);

                            // all parameters need to be submitted in order to avoid error
                            editForm.items.items[0].setValue(selected.title);
                            editForm.items.items[1].setValue(selected.release_year);
                            editForm.items.items[2].setValue(selected.rental_duration);
                            editForm.items.items[3].setValue(selected.rental_rate);
                            editForm.items.items[4].setValue(selected.replacement_cost);
                            editForm.items.items[5].setValue(selected.special_features);
                            editForm.items.items[6].setValue(selected.rating);

                            let langStr = languages.data.items[selected.language_id-1].data.language;
                            // console.warn("language: ",languages.data.items[selected.language_id-1].data.language);
                            editForm.items.items[7].setValue(langStr);
                            editForm.items.items[8].setValue(selected.length);
                            editForm.items.items[9].setValue(selected.director);
                            editForm.items.items[10].setValue(selected.description);
                            editWin.show();   
                        }
                        
                    }
                    
                }
            });

            let delPanel = Ext.create('Ext.panel.Panel',{
                html:'<h3>Are you sure?<h3>',
            });
            let delWin = new Ext.Window({
                title:'Delete Movie(s)',
                layout:{
                    type:'hbox',
                    pack:'center'
                },
                id:'delWin',
                closeAction:'close',
                width:200,
                items:[
                    delPanel
                ],
                buttons:[
                    {
                        text:'Delete',
                        handler:function(){
                            let delData = Ext.getCmp('movieGrid').getSelectionModel().getSelection();

                            delData.forEach(i => {
                                let dataId = i.data.film_id;
                                console.warn('to delete data ids: ',dataId);

                                // request to delete by film id
                                Ext.Ajax.request({
                                    url:rootURL+'/delete',
                                    method:'DELETE',
                                    jsonData:true,
                                    params:{
                                        film_id:dataId,
                                    },
                                    success:function(response){
                                        console.log(response);
                                    },
                                    failure:function(response){
                                        console.warn('ERROR: ',response);
                                    }
                                })
                            });
                        }
                    },
                    {
                        text:'Cancel',
                        handler:function(){
                            delWin.hide();
                        }
                    }            
                ],
                buttonAlign:'center'
            });

            let delBtn = new Ext.create('Ext.Button',{
        	text:'Delete',
        	iconCls:'fa fa-trash',
            listeners:{
                    click:function(){
                        let sel = Ext.getCmp('movieGrid').getSelectionModel().getSelection();
                        if(sel.length<1)
                            Ext.Msg.alert('Invalid Operation','Select at least one row to edit');
                        else{
                            
                            delWin.show();
                        }
                    }
                }
            });
        

            let toolbar = Ext.create('Ext.toolbar.Toolbar',{
                dock:'top',
                layout:{
                    type:'hbox',
                    pack:'center'
                },
                items:[
                    {
                        xtype:'pagingtoolbar',
                        store:movieStore,
                        width:360,
                        displayInfo:false,
                        border:false
                    },
                    '-',
                    addBtn,
                    '-',
                    editBtn,
                    '-',
                    delBtn
                ]
            });

            let grid = Ext.create('Ext.grid.Panel',{
                id:'movieGrid',
                stripeRows:true,
                store:movieStore,
                title:'Movie Grid',
                collapsible:false,
                enableColumnMove:true,
                enableColumnResize:true,
                renderTo:'grid',
                width:'100%',
                height:'100%',
                layout:'fit',
                columns:[
                    // {
                    //     header:'ID',
                    //     dataIndex:'film_id',
                    //     sortable:true,
                    //     flex:.1
                    // },
                    {
                        header:'Title',
                        dataIndex:'title',
                        sortable:true,
                        flex:1
                    },
                    {
                        header:'Description',
                        dataIndex:'description',
                        sortable:true,
                        flex:1
                    },
                    {
                        header:'Rating',
                        dataIndex:'rating',
                        sortable:false,
                        flex:.5
                    },
                    {
                        header:'Special Features',
                        dataIndex:'special_features',
                        sortable:false,
                        flex:.5
                    },
                    {
                        header:'Release Year',
                        dataIndex:'release_year',
                        sortable:true,
                        flex:.5
                    },
                    {
                        header:'Director',
                        dataIndex:'director',
                        sortable:true,
                        flex:.5
                    },
                       
                ],
                selModel: {
                    injectCheckbox:'first',
                    checkOnly:true,
                    mode:'MULTI',
                    listeners:{
                        select:function(record,sm,index,eOpts){
                            // console.log('selected');
                            // console.log(record.selected.items[0].data);
                        }
                    }
                },
                selType:'checkboxmodel',
                dockedItems: [
                    toolbar
                ]
            });

            Ext.create('Ext.container.Viewport',{
                renderTo: 'container',
                items:[
                    searchForm,
                ]
            });

            Ext.create('Ext.container.Container',{
                renderTo:'grid',
                items:[
                    grid
                ]
    	    });

        });
        
        console.log(<%=request.getAttribute("data")%>);
 
    </script>
</head>
<body>	
	<div id="form"></div>
	<div id="grid"></div>
</body>
</html>


