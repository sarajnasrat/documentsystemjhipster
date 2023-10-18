import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Column } from 'primereact/column';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Paginator } from 'primereact/paginator';
import { IDocumentInfo } from 'app/shared/model/document-info.model';
import { getEntities } from './document-info.reducer';
import { InputText } from 'primereact/inputtext';
import { DataTable } from 'primereact/datatable';
import { Image } from 'primereact/image';
import axios from 'axios';
import { faHome, faRefresh, faSearch, faSync } from '@fortawesome/free-solid-svg-icons';
import { Button } from 'primereact/button';
import { AutoComplete } from 'primereact/autocomplete';
import { trim } from 'lodash';
export const DocumentInfo = () => {
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState(0);
  const location = useLocation();
  const navigate = useNavigate();
  const documentInfoEntity = useAppSelector(state => state.documentInfo.entity);
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const documentInfoList = useAppSelector(state => state.documentInfo.entities);
  const loading = useAppSelector(state => state.documentInfo.loading);
  const totalItems = useAppSelector(state => state.documentInfo.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };
  const [globalFilter, setGlobalFilter] = useState('');
  const onFilterInputChange = e => {
    setGlobalFilter(e.target.value);
  };
  // const onPageChange = event => {
  //   setPaginationState({
  //     ...paginationState,
  //     activePage: event.first / event.rows + 1,
  //     itemsPerPage: event.rows,
  //   });
  // };


  const onPageChange = event => {
    setCurrentPage(event.page);
    setPaginationState({
      ...paginationState,
      activePage: event.first / event.rows + 1,
      itemsPerPage: event.rows,
    });
  }; const [searchResults, setSearchResults] = useState([]);
  console.log("Search Result" + searchResults)
  const [selectNumber, setSelectNumber] = useState(null);
  const [selectSubject, setSelectSubject] = useState(null);
  const [selectOrganization, setSelectOrganization] = useState([]);
  const handleSearch = () => {
    if (selectNumber || selectSubject || selectOrganization) {
      axios
        .get(`http://localhost:8080/api/searchdocument`, {
          params: {
            number: selectNumber,
            subject: selectSubject,
            organization: selectOrganization,
          },
        })
        .then((response) => {
          const data = response.data;
          console.log("Data", data);
          setSearchResults(data);
        })
        .catch((error) => {
          console.error('Error:', error);
          setSearchResults([]);
        });
    } else {
      setSearchResults([]); // Clear search results if no search criteria are provided
    }
  };
  const onEmployeeSelect = (e) => {
    setSelectNumber(e.value);
    console.log("values" + e.value)
    // console.log(e.value)
  };
  const onSubjectSelect = (e) => {
    setSelectSubject(e.value);
  };
  const onOrganizationSelect = (e) => {
    setSelectOrganization(e.value);
  };
  const header = (
    <div className="table-header">
     <div className='d-flex justify-content-end'>
      
      {/* <div className="btns-wrap">
        <Button
          icon={<FontAwesomeIcon icon={faSync} spin={loading} />}
          onClick={handleSyncList}
         
          disabled={loading}
          severity="info"
         
          raised
        />
      </div> */}
      <div className="btns-wrap">
        <Button
          iconPos="left"
          size="small"
          icon={<FontAwesomeIcon icon="plus" />}
          label={translate('documentmanagementsytemApp.documentInfo.home.createLabel')}
          onClick={e => {
            navigate(`/document-info/new`);
          }}
          severity="info"
        />
      </div>
 
    </div>
  <div className='d-flex justity-content-end'>
  <div className="col-md-4 sm-12">
          <div>  <label htmlFor="" className='form-label'>Document Number:</label></div>
          <AutoComplete
            value={selectNumber}
            field="number"
            onChange={onEmployeeSelect}
            className="search-input"
          />
        </div>
        <div className="col-md-4 sm-12">
          <div>  <label htmlFor="" className='form-label'>Subject:</label></div>
          <AutoComplete
            value={selectSubject}
            field="subject"
            onChange={onSubjectSelect}
            className="search-input"
          />
        </div>
        <div className="col-md-4 sm-12">
          <div>  <label htmlFor="" className='form-label'>Organization:</label></div>
          <AutoComplete
            value={selectOrganization}
            field="organization"
            onChange={onOrganizationSelect}
            className="search-input"
          />
        </div>
   
  </div>

      <div className="col-md-6 mt-2">
        <Button onClick={handleSearch} rounded={true} icon="pi pi-search" className="search-btn"></Button>
      </div>
     
    </div>
  
  );






  // const actionTemplate = rowData => {
  //   return (
  //     <div className="p-buttonset">
  //       <Link to={`/document-info/${rowData.id}`} className="btn btn-info" title="View">
  //         <FontAwesomeIcon icon="eye" />
  //       </Link>
  //       <Link to={`/document-info/${rowData.id}/edit`} className="btn btn-primary" title="Edit">
  //         <FontAwesomeIcon icon="pencil-alt" />
  //       </Link>
  //       <Link to={`/document-info/${rowData.id}/delete`} className="btn btn-danger" title="Delete">
  //         <FontAwesomeIcon icon="trash" />
  //       </Link>
  //     </div>
  //   );
  // };
  const actionTemplate = rowData => {
    // Use useNavigate to get the navigate function

    return (
      <div className="p-buttonset">
        <Button
          // label="View"
          icon="pi pi-eye"
          className="p-button-info"
          onClick={() => navigate(`/document-info/${rowData.id}`)}
        />
        <Button
          // label="Edit"
          
          icon="pi pi-pencil"
          className="p-button-primary"
          onClick={() => navigate(`/document-info/${rowData.id}/edit`)}
        />
        <Button
          // label="Delete"
          icon="pi pi-trash"
          className="p-button-danger"
          onClick={() => navigate(`/document-info/${rowData.id}/delete`)}
        />
      </div>
    );
  }
  const imageTemplate = rowData => {
    if (rowData.scanPath) {
      return (
        <img
          src={`data:${rowData.scanPathContentType};base64,${rowData.scanPath}`}
          style={{ maxHeight: '100px', maxWidth: '100px' }}
          alt="Scan Image"
        />
      );
    }
    return null;
  };



  return (
    <div>
      <h2 id="document-info-heading" data-cy="DocumentInfoHeading">
        <Translate contentKey="documentmanagementsytemApp.documentInfo.home.title">Document List</Translate>
      </h2>
      {header}
      <div className="table-responsive">
        <DataTable
          size='small'
          value={documentInfoList}
          onPage={onPageChange}
          totalRecords={totalItems}

          loading={loading}
          scrollable={true}
          scrollHeight="70vh"
          showGridlines
          stripedRows
          editMode="cell"
          width="700px"
          resizableColumns
          className='p-datatable-wrapper'
          dataKey="id"
          emptyMessage="No Document Infos found"
        >

          <Column field="number" header="Number"></Column>
          <Column field="registeredNumber" header="Registered Number" ></Column>
          <Column field="issuedate" header="Issuedate"></Column>
          <Column field="subject" header="Subject" ></Column>
          <Column field="dpriority" header="Dpriority" ></Column>
          {/* <Column field="scanPath" header="Scan Path" sortable filter body={imageTemplate} /> */}
          <Column field="content" header="Content" style={{ whiteSpace: 'normal' }}></Column>
          <Column field="organization" header="Organization" ></Column>
          <Column header="Actions" body={actionTemplate}></Column>
        </DataTable>
        <Paginator
          first={currentPage * paginationState.itemsPerPage}
          rows={paginationState.itemsPerPage}
          totalRecords={totalItems}
          onPageChange={onPageChange}
        />
      </div>
    </div>
  );
};

export default DocumentInfo;
