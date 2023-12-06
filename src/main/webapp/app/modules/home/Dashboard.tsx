import axios from 'axios';
import { Divider } from 'primereact/divider';
import React, { useEffect, useState } from 'react';
import { translate } from 'react-jhipster';
const Dashboard = () => {
  const [documentInof, setDocumentInfo] = useState(0);
  console.log(documentInof);
  useEffect(() => {
    const fetchNidResponses = async () => {
      try {
        const response = await axios.get('/api/document-infos/count');
        setDocumentInfo(response.data);
      } catch (error) {
        // console.log('Error fetching NID responses:', error);
      }
    };

    fetchNidResponses();
  }, []);
  // useEffect(()=>{
  //   const fetchNidResponses = async () => {
  //     try {
  //       const response = await axios.get("/api/document-infos/count");
  //       setDocumentInfo(response.data);
  //       console.log("Dataa"+response)

  //     } catch (error) {
  //       // console.log('Error fetching NID responses:', error);
  //     }
  //     fetchNidResponses();
  //   }}, []);
  const qtyArray = [
    { name: translate('home.dashboard.documentInfo'), qty: documentInof },

    // { name: "Distribution Permit Letters", qty: distPermitLitter }
  ];
  return (
    <div className="card container">
      <Divider align="left">
        <h2>{translate('home.dashboard.documentInfo')}</h2>
      </Divider>
      <div className="row card-wrapper">
        {qtyArray &&
          qtyArray.map((item, index) => (
            <div className="col-lg-3" key={index}>
              <div className="card">
                <div className="">{item.name}</div>
                {/* <div className="">{item.qty}</div> */}
                <div className="card-body">{item.qty ?? 0}</div>
              </div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default Dashboard;
