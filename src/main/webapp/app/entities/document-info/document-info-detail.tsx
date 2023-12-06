import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { TextFormat, Translate, byteSize, openFile, translate } from 'react-jhipster';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document-info.reducer';

export const DocumentInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentInfoEntity = useAppSelector(state => state.documentInfo.entity);
  return (
    <div className="card">
      <Row>
        <Col md="8">
          <h2 data-cy="documentInfoDetailsHeading">
            <Translate contentKey="documentmanagementsytemApp.documentInfo.detail.title">DocumentInfo</Translate>
          </h2>
          <dl className="jh-entity-details">
            {/* <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.id}</dd> */}

            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.number')}:{' '}
              </label>
              {documentInfoEntity.number}
            </div>
            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.registeredNumber')}:{' '}
              </label>
              {documentInfoEntity.registeredNumber}
            </div>
            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.issuedate')}:{' '}
              </label>
              {documentInfoEntity.issuedate ? (
                <TextFormat value={documentInfoEntity.issuedate} type="date" format={APP_LOCAL_DATE_FORMAT} />
              ) : null}
            </div>

            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.subject')}:{' '}
              </label>
              {documentInfoEntity.subject}
            </div>
            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.dpriority')}:{' '}
              </label>
              {documentInfoEntity.dpriority}
            </div>
            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.scanPath')}:{' '}
              </label>
              {documentInfoEntity.scanPath ? (
                <div>
                  {documentInfoEntity.scanPathContentType ? (
                    <a onClick={openFile(documentInfoEntity.scanPathContentType, documentInfoEntity.scanPath)}>
                      <img
                        src={`data:${documentInfoEntity.scanPathContentType};base64,${documentInfoEntity.scanPath}`}
                        style={{ maxHeight: '100px' }}
                      />
                    </a>
                  ) : null}
                  <span>
                    {documentInfoEntity.scanPathContentType}, {byteSize(documentInfoEntity.scanPath)}
                  </span>
                </div>
              ) : null}
            </div>
            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.content')}:{' '}
              </label>
              {documentInfoEntity.content}
            </div>
            <div>
              <label style={{ fontWeight: 'bold' }} htmlFor="">
                {translate('documentmanagementsytemApp.documentInfo.organization')}:{' '}
              </label>
              {documentInfoEntity.organization}
            </div>
          </dl>
          <Button tag={Link} to="/document-info" replace color="info" data-cy="entityDetailsBackButton">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/document-info/${documentInfoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    </div>
  );
};

export default DocumentInfoDetail;
