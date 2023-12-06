import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import React from 'react';
import { Route } from 'react-router-dom';
import DocumentInfo from './document-info';
import DocumentInfoDeleteDialog from './document-info-delete-dialog';
import DocumentInfoDetail from './document-info-detail';
import DocumentInfoUpdate from './document-info-update';

const DocumentInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DocumentInfo />} />
    <Route path="new" element={<DocumentInfoUpdate />} />
    <Route path=":id">
      <Route index element={<DocumentInfoDetail />} />
      <Route path="edit" element={<DocumentInfoUpdate />} />
      <Route path="delete" element={<DocumentInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DocumentInfoRoutes;
