/* eslint-disable @typescript-eslint/no-misused-promises */
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { AutoComplete } from 'primereact/autocomplete';
import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Paginator } from 'primereact/paginator';
import React, { useEffect, useState } from 'react';
import { Translate, getSortState, translate } from 'react-jhipster';
import { useLocation, useNavigate } from 'react-router-dom';
import { Col, Row } from 'reactstrap';
import { searchDocuments } from '../document-info/document-info.reducer';
import { getEntities } from './document-info.reducer';
export const DocumentInfo = () => {
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState(0);
  const location = useLocation();
  const navigate = useNavigate();
  const documentInfoEntity = useAppSelector(state => state.documentInfo.entity);
  const [searchval, setSearchval] = useState(false);
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const documentInfoList = useAppSelector(state => state.documentInfo.entities);
  const loading = useAppSelector(state => state.documentInfo.loading);
  const totalItems = useAppSelector(state => state.documentInfo.totalItems);
  const [totalResults, setTotalResults] = useState(null);
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

  const handlePagination = () => {
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });
  };

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
  };
  const [searchResults, setSearchResults] = useState([]);
  const [selectNumber, setSelectNumber] = useState([]);
  const [selectSubject, setSelectSubject] = useState([]);
  const [selectOrganization, setSelectOrganization] = useState([]);
  const handleSearch = async () => {
    try {
      let newResults = [];
      if (selectNumber || selectSubject || selectOrganization) {
        const results = await searchDocuments(selectNumber, selectSubject, selectOrganization, currentPage, paginationState.itemsPerPage);
        newResults = results.content;
        setTotalResults(results.totalElements);
        setSearchval(true);
      } else {
        const entities = getEntities({
          page: currentPage - 1,
          size: paginationState.itemsPerPage,
          sort: `${paginationState.sort},${paginationState.order}`,
        });

        setTotalResults(totalItems);
      }

      setSearchResults(newResults);
    } catch (error) {
      console.error('Error:', error.message);
      setSearchResults([]);
      setTotalResults(0);
    }
  };
  const onEmployeeSelect = e => {
    setSelectNumber(e.value);
  };
  const onSubjectSelect = e => {
    setSelectSubject(e.value);
  };
  const onOrganizationSelect = e => {
    setSelectOrganization(e.value);
  };
  useEffect(() => {
    if (searchval) {
      handleSearch();
    }
  }, [currentPage]);
  const header = (
    <div className="table-header">
      <div className="d-flex justify-content-between">
        <div>
          <h2 id="document-info-heading" data-cy="DocumentInfoHeading">
            <Translate contentKey="documentmanagementsytemApp.documentInfo.home.title">Document List</Translate>
          </h2>
        </div>
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
      <div className="d-flex justity-content-end">
        <Row>
          <Col md="4" sm="12">
            <div className="col-md-4 sm-12">
              <div>
                {' '}
                <label htmlFor="" className="form-label">
                  {translate('documentmanagementsytemApp.documentInfo.number')}:
                </label>
              </div>
              <AutoComplete value={selectNumber} field="number" onChange={onEmployeeSelect} className="search-input" />
            </div>
          </Col>
          <Col md="4" sm="12">
            <div className="col-md-4 sm-12">
              <div>
                {' '}
                <label htmlFor="" className="form-label">
                  {translate('documentmanagementsytemApp.documentInfo.subject')}:
                </label>
              </div>
              <AutoComplete value={selectSubject} field="subject" onChange={onSubjectSelect} className="search-input" />
            </div>
          </Col>
          <Col md="4" sm="12">
            <div className="col-md-4 sm-12">
              <div>
                {' '}
                <label htmlFor="" className="form-label">
                  {translate('documentmanagementsytemApp.documentInfo.organization')}:
                </label>
              </div>
              <AutoComplete value={selectOrganization} field="organization" onChange={onOrganizationSelect} className="search-input" />
            </div>
          </Col>
        </Row>
      </div>

      <div className="col-md-6 mt-2">
        <Button onClick={handleSearch} rounded={true} icon="pi pi-search" className="search-btn"></Button>
      </div>
    </div>
  );
  const actionTemplate = rowData => {
    return (
      <div className="p-buttonset">
        <Button
          // label="View"
          icon="pi pi-eye"
          className="p-button-info"
          onClick={() => navigate(`/document-info/${rowData.id}`)}
        />
        <Button icon="pi pi-trash" className="p-button-danger" onClick={() => navigate(`/document-info/${rowData.id}/delete`)} />
      </div>
    );
  };
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
    <div className="card" style={{ height: '500px' }}>
      {header}
      <div className="table-responsive">
        <DataTable
          size="small"
          value={searchResults}
          onPage={onPageChange}
          totalRecords={totalResults}
          loading={loading}
          scrollable={true}
          showGridlines
          stripedRows
          editMode="cell"
          width="700px"
          resizableColumns
          className="p-datatable-wrapper"
          dataKey="id"
          emptyMessage={translate('documentmanagementsytemApp.documentInfo.home.notFound')}
        >
          <Column field="id" header={translate('documentmanagementsytemApp.documentInfo.id')}></Column>
          <Column field="number" header={translate('documentmanagementsytemApp.documentInfo.number')}></Column>
          <Column field="registeredNumber" header={translate('documentmanagementsytemApp.documentInfo.registeredNumber')}></Column>
          <Column field="issuedate" header={translate('documentmanagementsytemApp.documentInfo.issuedate')}></Column>
          <Column field="subject" header={translate('documentmanagementsytemApp.documentInfo.subject')}></Column>
          <Column field="dpriority" header={translate('documentmanagementsytemApp.documentInfo.dpriority')}></Column>
          {/* <Column field="scanPath" header="Scan Path" sortable filter body={imageTemplate} /> */}
          <Column
            field="content"
            header={translate('documentmanagementsytemApp.documentInfo.content')}
            style={{ whiteSpace: 'normal' }}
          ></Column>
          <Column field="organization" header={translate('documentmanagementsytemApp.documentInfo.organization')}></Column>
          <Column header={translate('documentmanagementsytemApp.documentInfo.action')} body={actionTemplate}></Column>
        </DataTable>
        {totalResults && (
          <div className="p-rtl-paginator-container">
            <div className="p-rtl-paginator">
              <Paginator
                first={currentPage * paginationState.itemsPerPage}
                rows={paginationState.itemsPerPage}
                totalRecords={totalResults}
                onPageChange={onPageChange}
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default DocumentInfo;
